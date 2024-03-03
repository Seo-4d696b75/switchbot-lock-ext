package com.seo4d696b75.android.switchbot_lock_ext.data.geo

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockGeofenceDao
import com.seo4d696b75.android.switchbot_lock_ext.data.db.LockGeofenceEntity
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.GeofenceRepository
import com.seo4d696b75.android.switchbot_lock_ext.domain.geo.LockGeofence
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject


class GeofenceRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val dao: LockGeofenceDao,
) : GeofenceRepository {

    private val geofenceClient = LocationServices.getGeofencingClient(context)

    override val geofenceFlow = dao
        .getAll()
        .map { list -> list.map { it.toModel() } }

    override suspend fun addGeofence(geofence: LockGeofence): String {
        val geofenceWithId = geofence.copy(
            id = UUID.randomUUID().toString(),
        )
        dao.add(LockGeofenceEntity.fromModel(geofenceWithId))
        registerGeofences(listOf(geofenceWithId))
        return geofenceWithId.id
    }

    override suspend fun updateGeofence(geofence: LockGeofence) {
        val old = geofenceFlow.first().first { it.id == geofence.id }
        val entity = LockGeofenceEntity.fromModel(geofence)
        dao.update(entity)
        val isTurnOff = old.enabled && !geofence.enabled
        val isTurnOn = !old.enabled && geofence.enabled
        val isGeofenceChanged = geofence.enabled &&
                (old.lat != geofence.lat ||
                        old.lng != geofence.lng ||
                        old.radius != geofence.radius)
        if (isTurnOff || isGeofenceChanged) {
            unregisterGeofence(listOf(old))
        }
        if (isTurnOn || isGeofenceChanged) {
            registerGeofences(listOf(geofence))
        }
    }

    override suspend fun removeGeofence(geofence: LockGeofence) {
        dao.remove(listOf(geofence.id))
        if (geofence.enabled) {
            unregisterGeofence(listOf(geofence))
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        locationManager ?: return false
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    @SuppressLint("MissingPermission")
    private fun registerGeofences(geofences: List<LockGeofence>) {
        if (!isLocationPermissionGranted() || !isLocationEnabled()) {
            Log.w("GeofenceRepository", "location not available")
            return
        }
        val geofenceRequest = GeofencingRequest.Builder().apply {
            addGeofences(
                geofences.map { it.toGeofence() },
            )
        }.build()
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        )
        geofenceClient.addGeofences(geofenceRequest, pendingIntent)
            .apply {
                addOnSuccessListener {
                    Log.d("GeofenceRepository", "Success to add geofence")
                }
                addOnFailureListener {
                    Log.w("GeofenceRepository", "Failed to add geofence $it")
                    if (it is ApiException && it.statusCode == GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
                        // TODO check location setting and re-register geofences
                    }
                }
            }
    }

    private fun unregisterGeofence(geofences: List<LockGeofence>) {
        geofenceClient.removeGeofences(
            geofences.map { it.id }
        ).apply {
            addOnSuccessListener {
                Log.d("GeofenceRepository", "Success to remove geofence")
            }
            addOnFailureListener {
                Log.w("GeofenceRepository", "Failed to remove geofence $it")
            }
        }
    }
}

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface GeofenceRepositoryModule {
    @Binds
    fun bindGeofenceRepository(impl: GeofenceRepositoryImpl): GeofenceRepository
}

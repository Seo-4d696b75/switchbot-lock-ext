package com.seo4d696b75.android.switchbot_lock_ext.geo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent


class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        val event = GeofencingEvent.fromIntent(intent) ?: return
        if (event.hasError()) {
            val message =
                GeofenceStatusCodes.getStatusCodeString(event.errorCode)
            Log.d("GeofenceBroadcastReceiver", message)
            return
        }
        val transition = event.geofenceTransition
        val geofences = event.triggeringGeofences ?: emptyList()
        if (transition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Log.d(
                "GeofenceBroadcastReceiver",
                "Enter ${event.triggeringLocation}"
            )
            geofences.forEach {
                Log.d("GeofenceBroadcastReceiver", "id: ${it.requestId}")
            }
        } else {
            Log.d("GeofenceBroadcastReceiver", "unexpected transition")
        }
    }
}

package com.seo4d696b75.android.switchbot_lock_ext.data.geo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
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
        val geofences = event.triggeringGeofences ?: emptyList()
        val transition = event.geofenceTransition.toGeofenceTransition()
        Log.d(
            "GeofenceBroadcastReceiver",
            "transition: $transition",
        )
        geofences.forEach {
            Log.d(
                "GeofenceBroadcastReceiver",
                "geofence id: ${it.requestId}",
            )
        }
    }
}

package com.example.pjatkshoppinglist.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        val type = geofencingEvent.geofenceTransition
        for(geo in geofencingEvent.triggeringGeofences) {
            var text = ""
            if(type == Geofence.GEOFENCE_TRANSITION_ENTER)
                text = "You've entered ${intent.getStringExtra("name")}"
            else if (type == Geofence.GEOFENCE_TRANSITION_EXIT)
                text = "You've left ${intent.getStringExtra("name")}"
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()

            println("Jestem w broadcast")
        }
    }
}
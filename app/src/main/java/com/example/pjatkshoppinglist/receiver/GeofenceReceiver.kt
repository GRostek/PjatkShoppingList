package com.example.pjatkshoppinglist.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        for(geo in geofencingEvent.triggeringGeofences)
            Toast.makeText(context,"You've entered ${intent.getStringExtra("name")}", Toast.LENGTH_SHORT).show()
    }
}
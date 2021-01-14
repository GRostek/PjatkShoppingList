package com.example.pjatkshoppinglist.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.pjatkshoppinglist.service.GeofenceService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        val type = geofencingEvent.geofenceTransition
        for(geo in geofencingEvent.triggeringGeofences) {
            var text = ""
            var notiId = 0
            if(type == Geofence.GEOFENCE_TRANSITION_ENTER) {
                text = "You've entered ${intent.getStringExtra("name")}"
                notiId = 0
            }
            else if (type == Geofence.GEOFENCE_TRANSITION_EXIT) {
                text = "You've left ${intent.getStringExtra("name")}"
                notiId = 1
            }
            Toast.makeText(
                context,
                text,
                Toast.LENGTH_SHORT
            ).show()

            val service = Intent(context, GeofenceService::class.java)
            service.putExtra("msg", text)
            service.putExtra("noti_id", notiId)
            context.startService(service)
        }
    }
}
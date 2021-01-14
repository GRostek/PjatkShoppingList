package com.example.pjatkshoppinglist.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.pjatkshoppinglist.R

class GeofenceService: Service() {



    private var channelId = 100

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //val mapsActivity = Intent()
        //mapsActivity.component = ComponentName("com.example.pjatkshoppinglist", "com.example.pjatkshoppinglist.activity.MapsActivity")

        val notificationId = intent.getIntExtra("noti_id", 0)

        createChannel()


        /*val pendingIntent = PendingIntent.getActivity(
                this,
                notificationId++,
                mapsActivity,
                PendingIntent.FLAG_ONE_SHOT
        )*/

        val channelId = "default_channel"

        /*if(channelId == null){
            channelId = "default_channel"
        }*/

        val notification = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(intent.getStringExtra("msg"))
                //.setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build()

        NotificationManagerCompat.from(this).notify(notificationId,notification)


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(){
        val channel = NotificationChannel(
                "default_channel",
                "default_channel",
                NotificationManager.IMPORTANCE_DEFAULT
        )

        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }



}
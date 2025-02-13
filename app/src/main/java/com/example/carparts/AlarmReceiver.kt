package com.example.carparts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    val TAG = "PieceAlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive")

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, CarPartsApplication.CHANNEL_ID)
            .setContentTitle("Car Parts")
            .setContentText("Une nouvelle pièce a été ajoutée !")
            .setSmallIcon(R.drawable.ic_heart_filled)
            .build()

        manager.notify(1, notification)
    }
}

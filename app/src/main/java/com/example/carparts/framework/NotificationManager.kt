package com.example.carparts.framework

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.carparts.CarPartsApplication
import com.example.carparts.R

object NotificationHelper {
    fun showNotification(context: Context) {
        if (!CarPartsApplication.isAppInForeground) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification = NotificationCompat.Builder(context, CarPartsApplication.CHANNEL_ID)
                .setContentTitle("Car Parts")
                .setContentText("Une nouvelle pièce a été ajoutée !")
                .setSmallIcon(R.drawable.ic_heart_filled)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(1, notification)
        }
    }
}
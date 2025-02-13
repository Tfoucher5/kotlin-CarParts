package com.example.carparts

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class CarPartsApplication : Application() {

    companion object {
        const val CHANNEL_ID = "car_parts_channel"
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Car Parts Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for car parts"
            }
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

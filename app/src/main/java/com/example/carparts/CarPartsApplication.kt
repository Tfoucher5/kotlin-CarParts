package com.example.carparts

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log

class CarPartsApplication : Application() {

    companion object {
        const val CHANNEL_ID = "car_parts_channel"
        private const val TAG = "CarPartsApplication"

        @JvmStatic
        var isAppInForeground = false
            private set
    }

    private var activeActivities = 0

    override fun onCreate() {
        super.onCreate()
        setupNotificationChannel()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityStarted(activity: Activity) {
                if (activeActivities++ == 0) {
                    isAppInForeground = true
                    Log.d(TAG, "App went to foreground")
                }
            }

            override fun onActivityStopped(activity: Activity) {
                if (--activeActivities == 0) {
                    isAppInForeground = false
                    Log.d(TAG, "App went to background")
                }
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Car Parts Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for car parts"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
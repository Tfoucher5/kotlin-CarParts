package com.example.carparts.domain

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.carparts.CarPartsApplication

class ForegroundCheckReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "ForegroundCheckReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Checking if app is in foreground")

        if (CarPartsApplication.isAppInForeground) {
            Log.d(TAG, "App is in foreground, aborting broadcast")
            abortBroadcast()
        } else {
            Log.d(TAG, "App is in background, continuing broadcast")
        }
    }
}
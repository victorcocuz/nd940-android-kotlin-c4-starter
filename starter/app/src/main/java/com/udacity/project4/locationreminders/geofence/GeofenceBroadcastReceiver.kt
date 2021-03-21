package com.udacity.project4.locationreminders.geofence

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udacity.project4.locationreminders.savereminder.SaveReminderFragment.Companion.ACTION_GEOFENCE_EVENT

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == ACTION_GEOFENCE_EVENT) {
//            val geofencingEvent = GeofencingEvent.fromIntent(intent)
//
//            if (geofencingEvent.hasError()) {
//                val errorMessage = errorMessage(context, geofencingEvent.errorCode)
//                Timber.e("$errorMessage")
//                return
//            }
//
//            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
//                Timber.v("$context.getString(R.string.geofence_entered)")
//                val fenceId = when {
//                    geofencingEvent.triggeringGeofences.isNotEmpty() ->
//                        geofencingEvent.triggeringGeofences[0].requestId
//                    else -> {
//                        Timber.e("No Geofence Trigger Found! Abort mission!")
//                        return
//                    }
//                }

                GeofenceTransitionsJobIntentService.enqueueWork(context, intent)
//            }
        }

    }

//    private fun errorMessage(context: Context, errorCode: Int): String {
//        val resources = context.resources
//        return when (errorCode) {
//            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> resources.getString(
//                R.string.geofence_not_available
//            )
//            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> resources.getString(
//                R.string.geofence_too_many_geofences
//            )
//            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> resources.getString(
//                R.string.geofence_too_many_pending_intents
//            )
//            else -> resources.getString(R.string.unknown_geofence_error)
//        }
//    }
}
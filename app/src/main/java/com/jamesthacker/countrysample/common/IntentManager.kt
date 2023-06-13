package com.jamesthacker.countrysample.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.jamesthacker.countrysample.R
import com.jamesthacker.countrysample.domain.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

class IntentManager @Inject constructor(@ApplicationContext private val context: Context) {

    fun openGoogleMaps(latLng: LatLng) {
        val gmmIntentUri = Uri.parse("geo:${latLng.latitude},${latLng.longitude}?z=4")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        mapIntent.setPackage(GOOGLE_MAPS_PACKAGE)
        try {
            context.startActivity(mapIntent)
        } catch (ex: Exception) {
            Timber.w(ex)
            Toast.makeText(context, R.string.open_google_maps_error, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val GOOGLE_MAPS_PACKAGE = "com.google.android.apps.maps"
    }
}

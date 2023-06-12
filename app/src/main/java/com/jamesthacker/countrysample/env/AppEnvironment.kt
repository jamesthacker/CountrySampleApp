package com.jamesthacker.countrysample.env

import android.content.Context
import com.jamesthacker.countrysample.R
import com.jamesthacker.countrysample.data.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppEnvironment @Inject constructor(
    @ApplicationContext private val context: Context,
): Environment {
    override fun baseUrl(): String {
        return context.getString(R.string.base_api_url)
    }

    override fun enableLogging(): Boolean {
        return context.resources.getBoolean(R.bool.allow_logging)
    }
}

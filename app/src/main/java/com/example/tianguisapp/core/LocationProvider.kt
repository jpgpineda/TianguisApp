package com.example.tianguisapp.core

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import android.Manifest
import android.annotation.SuppressLint

class LocationProvider private constructor(context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)

    companion object {
        @Volatile
        private var INSTANCE: LocationProvider? = null

        fun getInstance(context: Context): LocationProvider {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationProvider(context).also { INSTANCE = it }
            }
        }
    }

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            val cancellationTokenSource = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
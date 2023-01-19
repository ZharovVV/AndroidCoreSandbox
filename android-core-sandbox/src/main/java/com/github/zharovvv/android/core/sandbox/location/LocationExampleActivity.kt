package com.github.zharovvv.android.core.sandbox.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.core.content.ContextCompat
import com.github.zharovvv.android.core.sandbox.R
import com.github.zharovvv.android.core.sandbox.databinding.ActivityLocationExampleBinding
import com.github.zharovvv.core.ui.activity.LogLifecycleAppCompatActivity
import java.util.*

class LocationExampleActivity : LogLifecycleAppCompatActivity() {

    private lateinit var binding: ActivityLocationExampleBinding
    private lateinit var requestLocationPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var locationManager: LocationManager

    private var currentLocationProvider: String = LocationManager.NETWORK_PROVIDER
    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            showLocation(location)
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermissionLauncher = registerForActivityResult(
            RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Доступ пердоставлен", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Доступ не пердоставлен!", Toast.LENGTH_SHORT).show()
                binding.textViewLatitude.text =
                    "Доступ не предоставлен! Координаты определить невозможно."
            }
        }
        with(binding) {
            registerForContextMenu(buttonRequestLocation)
            buttonRequestLocation.setOnClickListener {
                requestLocation()
            }
            buttonLocationSettings.setOnClickListener {
                startActivity(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
            }
        }
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    override fun onDestroy() {
        stopLocationUpdates()
        super.onDestroy()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_choose_location_provider, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.context_menu_item_network_provider -> {
                currentLocationProvider = LocationManager.NETWORK_PROVIDER
                binding.buttonRequestLocation.text = getString(R.string.start_network)
            }

            R.id.context_menu_item_gps_provider -> {
                currentLocationProvider = LocationManager.GPS_PROVIDER
                binding.buttonRequestLocation.text = getString(R.string.start_gps)
            }
        }
        return super.onContextItemSelected(item)
    }

    /**
     * Обычно вызывается в onResume. Слушатель удаляется в onPause.
     */
    private fun requestLocation() {
        stopLocationUpdates()
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            && checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        ) {
            locationManager.requestLocationUpdates(
                currentLocationProvider,
                0L,  //минимальное время (в миллисекундах) между получением данных
                1f,    //минимальное расстояние (в метрах).
                // Т.е. если ваше местоположение изменилось на указанное кол-во метров,
                // то вам придут новые координаты.
                locationListener
            )
        }
    }

    private fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    @Suppress("SameParameterValue")
    private fun checkPermission(permission: String): Boolean {
        when {
            ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                return true
            }

            shouldShowRequestPermissionRationale(permission) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Toast.makeText(
                    this,
                    "Доступ к геоданным нужен для работы этого приложения.",
                    Toast.LENGTH_SHORT
                ).show()
                requestLocationPermissionLauncher.launch(permission)
            }

            else -> {
                requestLocationPermissionLauncher.launch(permission)
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun showLocation(location: Location) {
        with(binding) {
            textViewLatitude.text = location.latitude.toString()
            textViewLongitude.text = location.longitude.toString()
            textViewInfo.text =
                "updateTime: ${Date(location.time)}\nprovider: ${location.provider}\nspeed: ${location.speed}"
        }

    }


}
package nik.borisov.weather.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import nik.borisov.weather.R
import nik.borisov.weather.presentation.fragments.ErrorFragment
import nik.borisov.weather.presentation.fragments.LoadingFragment
import nik.borisov.weather.presentation.fragments.LocationFragment
import nik.borisov.weather.presentation.fragments.MainFragment
import nik.borisov.weather.presentation.viewmodels.MainViewModel
import nik.borisov.weather.presentation.viewmodels.ViewModelFactory
import nik.borisov.weather.presentation.viewmodels.states.*
import nik.borisov.weather.utils.DialogManager
import nik.borisov.weather.utils.isPermissionGranted
import javax.inject.Inject

class MainActivity : AppCompatActivity(), DialogManager {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as WeatherApp).component
    }

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var pLauncher: ActivityResultLauncher<String>

    private val locationClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private var isLocationAccess = false

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            checkPermission()
            setViewModelState()
            observeViewModel()
            showFragmentWithoutBackStack(LoadingFragment.newInstance(), "Loading")
        }
    }

    override fun onResume() {
        super.onResume()
        refreshViewModelState()
    }

    private fun setViewModelState() {
        viewModel.setServiceState(
            ServiceState(
                isNetworkEnabled = isNetworkEnabled(),
                isLocationEnabled = checkLocationService()
            )
        )
    }

    private fun refreshViewModelState() {
        viewModel.refreshServiceState(
            ServiceState(
                isNetworkEnabled = isNetworkEnabled(),
                isLocationEnabled = checkLocationService()
            )
        )
    }

    private fun observeViewModel() {
        viewModel.viewModelState.observe(this) {
            when (it) {
                is ShowForecastFragment -> {
                    showFragmentWithoutBackStack(MainFragment.newInstance(), "Forecast")
                }
                is ShowLocationFragment -> {
                    showFragmentWithBackStack(LocationFragment.newInstance(), "Location")
                }
                is ShowLoading -> {
                    showFragmentWithoutBackStack(LoadingFragment.newInstance(), "Loading")
                }
                is ShowErrorFragment -> {
                    showFragmentWithoutBackStack(ErrorFragment.newInstance(), "Error")
                }
                is SetViewModelState -> {
                    setViewModelState()
                }
                is GetLocationFromService -> {
                    getCurrentLocation()
                }
            }
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            setupLocationPermissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
        isLocationAccess = true
    }

    private fun setupLocationPermissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) isLocationAccess = true
        }
    }

    private fun checkLocationService(): Boolean {
        if (!isLocationAccess) return false
        return if (isLocationEnabled()) {
            true
        } else {
            locationEnabledDialog(this, ::showLocationSettings)
            false
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getCurrentLocation() {
        val cancellationTokenSource = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationClient.getCurrentLocation(
            Priority.PRIORITY_LOW_POWER,
            cancellationTokenSource.token
        ).addOnCompleteListener {
            viewModel.getForecast("${it.result.latitude},${it.result.longitude}")
        }
    }

    private fun isNetworkEnabled(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun showFragmentWithBackStack(instance: Fragment, tag: String) {
        val currentInstance = supportFragmentManager.findFragmentByTag(tag)
        if (currentInstance == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, instance, tag)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showFragmentWithoutBackStack(instance: Fragment, tag: String) {
        val currentInstance = supportFragmentManager.findFragmentByTag(tag)
        if (currentInstance == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, instance, tag)
                .commit()
        }
    }

    private fun showLocationSettings() {
        startActivity((Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
    }
}
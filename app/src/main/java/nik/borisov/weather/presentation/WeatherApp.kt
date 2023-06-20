package nik.borisov.weather.presentation

import android.app.Application
import nik.borisov.weather.di.DaggerApplicationComponents

class WeatherApp : Application() {

    val component by lazy {
        DaggerApplicationComponents.create()
    }
}
package nik.borisov.weather.presentation

import android.app.Application
import nik.borisov.weather.di.ApplicationComponent
import nik.borisov.weather.di.DaggerApplicationComponent

class WeatherApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory()
            .create(this)
    }
}
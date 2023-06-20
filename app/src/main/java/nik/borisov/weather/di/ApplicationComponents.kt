package nik.borisov.weather.di

import dagger.Component
import nik.borisov.weather.presentation.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponents {

    fun inject(activity: MainActivity)
}
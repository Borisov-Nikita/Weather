package nik.borisov.weather.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import nik.borisov.weather.presentation.MainActivity

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
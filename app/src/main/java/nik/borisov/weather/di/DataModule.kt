package nik.borisov.weather.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import nik.borisov.weather.data.RepositoryImpl
import nik.borisov.weather.data.database.AppDatabase
import nik.borisov.weather.data.database.dao.ForecastDao
import nik.borisov.weather.data.network.ApiFactory
import nik.borisov.weather.data.network.ApiService
import nik.borisov.weather.domain.repositories.Repository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

    companion object {

        @ApplicationScope
        @Provides
        fun provideForecastDao(application: Application): ForecastDao {
            return AppDatabase.getInstance(application).getForecastDao()
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}
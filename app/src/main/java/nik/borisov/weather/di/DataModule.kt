package nik.borisov.weather.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import nik.borisov.weather.data.RepositoryImpl
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
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}
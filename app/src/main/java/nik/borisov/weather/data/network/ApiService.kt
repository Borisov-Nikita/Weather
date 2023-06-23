package nik.borisov.weather.data.network

import nik.borisov.weather.data.network.models.ForecastWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/forecast.json?key=390c3d80f6ce42c2afa111322231506&&days=3&aqi=no&alerts=no")
    suspend fun loadForecast(@Query("q") location: String): Response<ForecastWeatherResponse>
}
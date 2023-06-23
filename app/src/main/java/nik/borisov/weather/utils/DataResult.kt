package nik.borisov.weather.utils

sealed class DataResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T?) : DataResult<T>(data = data)
    class NetworkError<T>(message: String?) : DataResult<T>(message = message)
    class DatabaseError<T>(message: String?) : DataResult<T>(message = message)
    class LocationError<T>(message: String?) : DataResult<T>(message = message)
    class Loading<T>() : DataResult<T>()
}

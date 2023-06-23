package nik.borisov.weather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import nik.borisov.weather.databinding.ForecastItemBinding
import nik.borisov.weather.presentation.models.ForecastHourUi

class ForecastHoursAdapter :
    ListAdapter<ForecastHourUi, ForecastViewHolder>(ForecastHourDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder((binding))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastHour = currentList[position]
        with(holder.binding) {
            dateTimeTextView.text = forecastHour.time
            conditionTextView.text = forecastHour.conditionText
            tempTextView.text = forecastHour.temp
            Picasso.get()
                .load(forecastHour.conditionIcon)
                .into(weatherImageView)
            windTextView.text = forecastHour.windSpeed
            precipitationTextView.text = forecastHour.chanceOfPrecipitation
            humidityTextView.text = forecastHour.humidity
        }
    }
}
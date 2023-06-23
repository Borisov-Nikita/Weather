package nik.borisov.weather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import nik.borisov.weather.databinding.ForecastItemBinding
import nik.borisov.weather.presentation.models.ForecastDayUi

class ForecastDayAdapter :
    ListAdapter<ForecastDayUi, ForecastViewHolder>(ForecastDayDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastDay = currentList[position]
        with(holder.binding) {
            dateTimeTextView.text = forecastDay.date
            conditionTextView.text = forecastDay.conditionText
            tempTextView.text = forecastDay.maxMinTemp
            Picasso.get()
                .load(forecastDay.conditionIcon)
                .into(weatherImageView)
            windTextView.text = forecastDay.maxWindSpeed
            precipitationTextView.text = forecastDay.chanceOfPrecipitation
            humidityTextView.text = forecastDay.avgHumidity
        }
    }
}
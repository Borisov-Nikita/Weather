package nik.borisov.weather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import nik.borisov.weather.databinding.ForecastDayItemBinding
import nik.borisov.weather.presentation.models.ForecastDayUi

class ForecastDayAdapter :
    ListAdapter<ForecastDayUi, ForecastDayViewHolder>(ForecastDayDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDayViewHolder {
        val binding = ForecastDayItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastDayViewHolder, position: Int) {
        val forecastDay = currentList[position]
        with(holder.binding) {
            dateTextView.text = forecastDay.date
            conditionTextView.text = forecastDay.conditionText
            tempTextView.text = forecastDay.maxMinTemp
            Picasso.get()
                .load(forecastDay.conditionIcon)
                .into(weatherImageView)
        }
    }
}
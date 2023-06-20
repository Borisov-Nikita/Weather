package nik.borisov.weather.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import nik.borisov.weather.databinding.ForecastHourItemBinding
import nik.borisov.weather.presentation.models.ForecastHourUi

class ForecastHoursAdapter :
    ListAdapter<ForecastHourUi, ForecastHourViewHolder>(ForecastHourDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHourViewHolder {
        val binding = ForecastHourItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastHourViewHolder((binding))
    }

    override fun onBindViewHolder(holder: ForecastHourViewHolder, position: Int) {
        val forecastHour = currentList[position]
        with(holder.binding) {
            timeTextView.text = forecastHour.time
            conditionTextView.text = forecastHour.conditionText
            tempTextView.text = forecastHour.temp
            Picasso.get()
                .load(forecastHour.conditionIcon)
                .into(weatherImageView)
        }
    }
}
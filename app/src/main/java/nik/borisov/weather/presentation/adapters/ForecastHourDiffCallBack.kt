package nik.borisov.weather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import nik.borisov.weather.presentation.models.ForecastHourUi

class ForecastHourDiffCallBack : DiffUtil.ItemCallback<ForecastHourUi>() {

    override fun areItemsTheSame(oldItem: ForecastHourUi, newItem: ForecastHourUi): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: ForecastHourUi, newItem: ForecastHourUi): Boolean {
        return oldItem == newItem
    }

}
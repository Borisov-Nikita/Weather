package nik.borisov.weather.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import nik.borisov.weather.presentation.models.ForecastDayUi

class ForecastDayDiffCallBack : DiffUtil.ItemCallback<ForecastDayUi>() {
    override fun areItemsTheSame(oldItem: ForecastDayUi, newItem: ForecastDayUi): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: ForecastDayUi, newItem: ForecastDayUi): Boolean {
        return oldItem == newItem
    }
}
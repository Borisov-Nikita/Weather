package nik.borisov.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import nik.borisov.weather.R
import nik.borisov.weather.databinding.FragmentMainBinding
import nik.borisov.weather.presentation.MainActivity
import nik.borisov.weather.presentation.adapters.ForecastViewPagerAdapter
import nik.borisov.weather.presentation.viewmodels.MainViewModel
import nik.borisov.weather.presentation.viewmodels.states.SetViewModelState
import nik.borisov.weather.presentation.viewmodels.states.ShowLocationFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { (activity as MainActivity).viewModelFactory }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw NullPointerException("FragmentMainBinding is null")

    private val viewPagerFragmentList = listOf(
        ForecastHoursFragment.newInstance(),
        ForecastDaysFragment.newInstance()
    )

    private val tabLayoutTitleList by lazy {
        listOf(
            getString(R.string.hours),
            getString(R.string.days)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        observeViewModel()
        setupClickListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupViewPager() {
        val viewPager = binding.forecastViewPager
        viewPager.adapter = ForecastViewPagerAdapter(
            viewPagerFragmentList,
            activity as FragmentActivity
        )
        val tabLayout = binding.hourDayTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabLayoutTitleList[position]
        }.attach()
    }

    private fun observeViewModel() {
        viewModel.forecast.observe(viewLifecycleOwner) {
            with(binding.currentWeatherLayout) {
                dateTimeTextView.text = it.currentWeather.lastUpdate
                Picasso.get()
                    .load(it.currentWeather.conditionIcon)
                    .into(weatherImageView)
                tempTextView.text = it.currentWeather.temp
                conditionTextView.text = it.currentWeather.conditionText
                feelsLikeTempTextView.text = it.currentWeather.tempFeelsLike
                windTextView.text = it.currentWeather.windSpeed
                precipitationTextView.text = it.currentWeather.chanceOfPrecipitation
                humidityTextView.text = it.currentWeather.humidity
            }
            binding.collapsingToolbarLayout.title = it.location
        }
    }

    private fun setupClickListeners() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.search_location_menu_item -> {
                    viewModel.setViewModelState(ShowLocationFragment)
                    true
                }
                R.id.refresh_forecast_menu_item -> {
                    viewModel.setViewModelState(SetViewModelState)
                    true
                }
                else -> false
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
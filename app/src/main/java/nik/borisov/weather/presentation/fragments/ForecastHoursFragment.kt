package nik.borisov.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import nik.borisov.weather.databinding.FragmentForecastHoursBinding
import nik.borisov.weather.presentation.MainActivity
import nik.borisov.weather.presentation.adapters.ForecastHoursAdapter
import nik.borisov.weather.presentation.viewmodels.MainViewModel

class ForecastHoursFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { (activity as MainActivity).viewModelFactory }

    private var _binding: FragmentForecastHoursBinding? = null
    private val binding: FragmentForecastHoursBinding
        get() = _binding ?: throw NullPointerException("FragmentForecastHoursBinding is null")

    private val adapter = ForecastHoursAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForecastHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.forecast.observe(viewLifecycleOwner) {
            adapter.submitList(it.forecastHours)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.hoursRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ForecastHoursFragment()
    }
}
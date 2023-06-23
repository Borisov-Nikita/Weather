package nik.borisov.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import nik.borisov.weather.databinding.FragmentLocationBinding
import nik.borisov.weather.presentation.MainActivity
import nik.borisov.weather.presentation.viewmodels.MainViewModel

class LocationFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { (activity as MainActivity).viewModelFactory }

    private var _binding: FragmentLocationBinding? = null
    private val binding: FragmentLocationBinding
        get() = _binding ?: throw NullPointerException("FragmentLocationBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupClickListener() {
        binding.searchLocationButton.setOnClickListener {
            viewModel.getForecast(
                binding.locationEditText.text.toString()
            )
            finishFragment()
        }
    }

    private fun finishFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {

        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}
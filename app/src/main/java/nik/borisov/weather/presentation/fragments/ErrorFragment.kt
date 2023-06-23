package nik.borisov.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import nik.borisov.weather.databinding.FragmentErrorBinding
import nik.borisov.weather.presentation.MainActivity
import nik.borisov.weather.presentation.viewmodels.MainViewModel

class ErrorFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels { (activity as MainActivity).viewModelFactory }

    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding
        get() = _binding ?: throw NullPointerException("FragmentErrorBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
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
        binding.retryButton.setOnClickListener {
            viewModel.retryGetForecast()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ErrorFragment()
    }
}
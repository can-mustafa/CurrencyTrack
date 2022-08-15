package com.mustafacan.currencytrack.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mustafacan.currencytrack.databinding.FragmentCurrencyBinding
import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.presentation.viewmodel.CurrencyViewModel
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

// Created by Mustafa Can on 15.08.2022.

class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAvailableCurrencies()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObservers() {

        lifecycleScope.launch {
            viewModel.availableCurrencyList.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        bindData(result.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        println("Cur error")
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        println("Cur loading")
                    }
                }

            }
        }
    }

    private fun bindData(currency: Currency?) {
        val list = listOf<String>("try", "usd")
        val arrayAdapter =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, list)
        binding.spFromCurrency.adapter = arrayAdapter
    }
}
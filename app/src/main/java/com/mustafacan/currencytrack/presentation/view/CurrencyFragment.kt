package com.mustafacan.currencytrack.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mustafacan.currencytrack.R
import com.mustafacan.currencytrack.databinding.FragmentCurrencyBinding
import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.presentation.viewmodel.CurrencyViewModel
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

// Created by Mustafa Can on 15.08.2022.

class CurrencyFragment : Fragment() {

    private var _binding: FragmentCurrencyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by inject()

    private var currenciesMap: HashMap<String, String> = hashMapOf()
    private var fromCurrency = ""
    private var toCurrency = ""

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
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {

        binding.btnConvert.setOnClickListener {
            if (binding.etFrom.text.isNullOrEmpty()) {
                binding.tvResult.apply {
                    text = "Enter amount!"
                    setTextColor(resources.getColor(R.color.red, null))
                }
                return@setOnClickListener
            }
            fromCurrency = binding.spFromCurrency.selectedItem.toString()
            toCurrency = binding.spToCurrency.selectedItem.toString()
            viewModel.getCurrencyValue(fromCurrency, toCurrency)
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewModel.availableCurrencyList.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.root.isClickable = true
                        bindData(result.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        binding.root.isClickable = true
                        binding.tvResult.apply {
                            text = result.message.toString()
                            setTextColor(resources.getColor(R.color.red, null))
                        }
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.root.isClickable = false
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.currencyValue.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        binding.root.isClickable = true
                        bindValueData(result.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.isVisible = false
                        binding.root.isClickable = true
                        binding.tvResult.apply {
                            text = result.message.toString()
                            setTextColor(resources.getColor(R.color.red, null))
                        }
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.root.isClickable = false
                    }
                }
            }
        }
    }

    private fun bindData(currency: Currency?) {
        currency?.let {
            currenciesMap["cad"] = "cad"
            currenciesMap["eur"] = "eur"
            currenciesMap["jpy"] = "jpy"
            currenciesMap["rub"] = "rub"
            currenciesMap["try"] = "try"
            currenciesMap["uah"] = "uah"
            currenciesMap["usd"] = "usd"
        }

        val arrayAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                currenciesMap.values.toMutableList()
            )
        binding.spFromCurrency.adapter = arrayAdapter
        binding.spToCurrency.adapter = arrayAdapter
    }

    private fun bindValueData(currencyValue: CurrencyValue?) {
        var toValue: Double = 0.0
        currencyValue?.let {
            when (toCurrency) {
                "cad" -> {
                    toValue = currencyValue.cad ?: 0.0
                }
                "eur" -> {
                    toValue = currencyValue.eur ?: 0.0
                }
                "jpy" -> {
                    toValue = currencyValue.jpy ?: 0.0
                }
                "rub" -> {
                    toValue = currencyValue.rub ?: 0.0
                }
                "try" -> {
                    toValue = currencyValue.tryl ?: 0.0
                }
                "uah" -> {
                    toValue = currencyValue.uah ?: 0.0
                }
                "usd" -> {
                    toValue = currencyValue.usd ?: 0.0
                }
                else -> {
                    toValue = currencyValue.usd ?: 0.0
                }
            }
            binding.tvDate.text = "Date: ${currencyValue.date}"
            val resultValue = toValue * binding.etFrom.text.toString().toDouble()
            val roundedResultValue = String.format("%.2f", resultValue).toDouble()
            binding.tvResult.apply {
                text =
                    "${binding.etFrom.text.toString()} $fromCurrency = " +
                            "$roundedResultValue $toCurrency"
                setTextColor(resources.getColor(R.color.black, null))
            }
        }
    }
}
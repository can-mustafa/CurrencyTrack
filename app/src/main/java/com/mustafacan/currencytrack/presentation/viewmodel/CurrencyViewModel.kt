package com.mustafacan.currencytrack.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.domain.usecases.CurrencyUseCases
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

// Created by Mustafa Can on 14.08.2022.

class CurrencyViewModel(private val useCase: CurrencyUseCases) : ViewModel() {

    private val _availableCurrencyList =
        MutableStateFlow<Resource<Currency>>(Resource.Loading())
    val availableCurrencyList: StateFlow<Resource<Currency>> = _availableCurrencyList

    private val _currencyValue = MutableStateFlow<Resource<CurrencyValue>>(Resource.Loading())
    val currencyValue: StateFlow<Resource<CurrencyValue>> = _currencyValue

    fun getAvailableCurrencies() {

        viewModelScope.launch {
            _availableCurrencyList.emit(Resource.Loading())
            delay(2000)
            try {
                val response = useCase.getCurrenciesUseCase()
                _availableCurrencyList.emit(Resource.Success(response))
            } catch (e: Exception) {
                _availableCurrencyList.emit(Resource.Error(e.localizedMessage))
            }
        }
    }

    fun getCurrencyValue(currencyCode: String, secondCurrencyCode: String) {

        useCase.getCurrencyValueUseCase(currencyCode, secondCurrencyCode).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _currencyValue.emit(Resource.Success(result.data ?: CurrencyValue(null, null)))
                }
                is Resource.Error -> {
                    _currencyValue.emit(Resource.Error(result.message ?: "Unexpected error"))
                }
                is Resource.Loading -> {
                    _currencyValue.emit(Resource.Loading())
                }
            }
        }
    }
}
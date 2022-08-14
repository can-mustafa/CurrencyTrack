package com.mustafacan.currencytrack.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.domain.usecases.CurrencyUseCases
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach

// Created by Mustafa Can on 14.08.2022.

class CurrencyViewModel(private val useCase: CurrencyUseCases) : ViewModel() {


    private val _availableCurrencyList =
        MutableStateFlow<Resource<List<Currency>>>(Resource.Loading())
    val availableCurrencyList: StateFlow<Resource<List<Currency>>> = _availableCurrencyList

    private val _currencyValue = MutableStateFlow<Resource<CurrencyValue>>(Resource.Loading())
    val currencyValue: StateFlow<Resource<CurrencyValue>> = _currencyValue

    fun getAvailableCurrencies() {

        useCase.getCurrenciesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _availableCurrencyList.emit(Resource.Success(result.data ?: emptyList()))
                }
                is Resource.Error -> {
                    _availableCurrencyList.emit(
                        Resource.Error(
                            result.message ?: "Unexpected error"
                        )
                    )
                }
                is Resource.Loading -> {
                    _availableCurrencyList.emit(Resource.Loading())
                }
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
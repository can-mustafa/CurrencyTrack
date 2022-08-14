package com.mustafacan.currencytrack.domain.usecases

// Created by Mustafa Can on 14.08.2022.

data class CurrencyUseCases(
    val getCurrenciesUseCase: GetCurrenciesUseCase,
    val getCurrencyValueUseCase: GetCurrencyValueUseCase,
    val searchCurrencyUseCase: SearchCurrencyUseCase
)
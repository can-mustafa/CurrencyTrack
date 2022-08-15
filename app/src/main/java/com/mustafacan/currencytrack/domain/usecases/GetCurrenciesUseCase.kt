package com.mustafacan.currencytrack.domain.usecases

import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository

// Created by Mustafa Can on 14.08.2022.

class GetCurrenciesUseCase(private val repository: CurrencyRepository) {

    suspend operator fun invoke(): Currency {
        return repository.getAvailableCurrencies()
    }
}
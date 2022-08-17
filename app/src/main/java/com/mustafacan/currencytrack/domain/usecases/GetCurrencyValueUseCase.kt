package com.mustafacan.currencytrack.domain.usecases

import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository

// Created by Mustafa Can on 14.08.2022.

class GetCurrencyValueUseCase(private val repository: CurrencyRepository) {

    suspend operator fun invoke(
        fromCurrencyCode: String,
        toCurrencyCode: String
    ): CurrencyValue {
        return repository.getCurrencyValue(fromCurrencyCode, toCurrencyCode)
    }
}
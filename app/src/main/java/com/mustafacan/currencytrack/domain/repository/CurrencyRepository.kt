package com.mustafacan.currencytrack.domain.repository

import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue

// Created by Mustafa Can on 14.08.2022.

interface CurrencyRepository {
    suspend fun getAvailableCurrencies(): Currency
    suspend fun getCurrencyValue(fromCurrencyCode: String, toCurrencyCode: String): CurrencyValue
}
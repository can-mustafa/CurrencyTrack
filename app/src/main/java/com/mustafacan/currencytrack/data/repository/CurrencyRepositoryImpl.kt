package com.mustafacan.currencytrack.data.repository

import com.mustafacan.currencytrack.data.remote.CurrencyApi
import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository

// Created by Mustafa Can on 14.08.2022.

class CurrencyRepositoryImpl(private val currencyApi: CurrencyApi) : CurrencyRepository {

    override suspend fun getAvailableCurrencies(): Currency {
        return currencyApi.getCurrencies()
    }

    override suspend fun getCurrencyValue(
        currencyCode: String,
        secondCurrencyCode: String
    ): CurrencyValue {
        return currencyApi.getCurrencyValue(currencyCode, secondCurrencyCode)
    }
}
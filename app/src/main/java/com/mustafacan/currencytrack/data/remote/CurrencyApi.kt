package com.mustafacan.currencytrack.data.remote

import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.model.CurrencyValue
import retrofit2.http.GET
import retrofit2.http.Path

// Created by Mustafa Can on 14.08.2022.

interface CurrencyApi {

    @GET("currency-api@1/latest/currencies.json")
    suspend fun getCurrencies(): Currency

    @GET("currency-api@1/latest/currencies/{currency}/{currencySecond}.json")
    suspend fun getCurrencyValue(
        @Path("currency") currency: String,
        @Path("currencySecond") currencySecond: String
    ): CurrencyValue
}
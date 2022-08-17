package com.mustafacan.currencytrack.domain.model

import com.google.gson.annotations.SerializedName

// Created by Mustafa Can on 14.08.2022.

data class CurrencyValue(
    val date: String?,
    val cad: Double?,
    val eur: Double?,
    val jpy: Double?,
    val rub: Double?,
    @SerializedName("try")
    val tryl: Double?,
    val uah: Double?,
    val usd: Double?,
)
package com.mustafacan.currencytrack.domain.model

import com.google.gson.annotations.SerializedName

// Created by Mustafa Can on 14.08.2022.

data class Currency(
    val bnb: String,
    val btc: String,
    val busd: String,
    val cad: String,
    val chz: String,
    val eur: String,
    val jpy: String,
    val rub: String,
    @SerializedName("try")
    val tryl: String,
    val uah: String,
    val usd: String,
)
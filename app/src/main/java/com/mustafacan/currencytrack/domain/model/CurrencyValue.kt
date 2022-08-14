package com.mustafacan.currencytrack.domain.model

// Created by Mustafa Can on 14.08.2022.

data class CurrencyValue(
    val date: Date?,
    val currency: Currency?
)

data class Date(
    val dateLabel: String,
    val date: String
)
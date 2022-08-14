package com.mustafacan.currencytrack.domain.usecases

import com.mustafacan.currencytrack.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Created by Mustafa Can on 14.08.2022.

class SearchCurrencyUseCase {

    operator fun invoke(
        searchQuery: String,
        currentList: List<Currency>
    ): Flow<List<Currency>> {
        val newList: MutableList<Currency> = mutableListOf()
        return flow {
            currentList.forEachIndexed { _, currency ->
                if (currency.title.contains(searchQuery)) {
                    newList.add(currency)
                }
            }
            newList.toList()
        }
    }
}
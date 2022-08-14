package com.mustafacan.currencytrack.domain.usecases

import com.mustafacan.currencytrack.domain.model.Currency
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

// Created by Mustafa Can on 14.08.2022.

class GetCurrenciesUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(): Flow<Resource<List<Currency>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val currencies = repository.getAvailableCurrencies()
                emit(Resource.Success(currencies))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "Unexpected error"))
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        e.localizedMessage ?: "Couldn't reach server. Check internet connection"
                    )
                )
            }
        }
    }
}
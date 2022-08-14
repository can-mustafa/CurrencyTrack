package com.mustafacan.currencytrack.domain.usecases

import com.mustafacan.currencytrack.domain.model.CurrencyValue
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository
import com.mustafacan.currencytrack.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

// Created by Mustafa Can on 14.08.2022.

class GetCurrencyValueUseCase(private val repository: CurrencyRepository) {

    operator fun invoke(
        currencyCode: String,
        secondCurrencyCode: String
    ): Flow<Resource<CurrencyValue>> {
        return flow {
            try {
                emit(Resource.Loading())
                val currencyValue = repository.getCurrencyValue(currencyCode, secondCurrencyCode)
                emit(Resource.Success(currencyValue))
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
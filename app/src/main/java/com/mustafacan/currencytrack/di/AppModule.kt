package com.mustafacan.currencytrack.di

import com.mustafacan.currencytrack.data.remote.CurrencyApi
import com.mustafacan.currencytrack.data.repository.CurrencyRepositoryImpl
import com.mustafacan.currencytrack.domain.repository.CurrencyRepository
import com.mustafacan.currencytrack.domain.usecases.CurrencyUseCases
import com.mustafacan.currencytrack.domain.usecases.GetCurrenciesUseCase
import com.mustafacan.currencytrack.domain.usecases.GetCurrencyValueUseCase
import com.mustafacan.currencytrack.domain.usecases.SearchCurrencyUseCase
import com.mustafacan.currencytrack.presentation.viewmodel.CurrencyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Created by Mustafa Can on 14.08.2022.

class AppModule {

    companion object {
        val module = module {

            viewModel { CurrencyViewModel(get()) }

            single<CurrencyApi> {

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofit.create(CurrencyApi::class.java)
            }

            single<CurrencyRepository> {
                CurrencyRepositoryImpl(get())
            }

            single {
                CurrencyUseCases(
                    GetCurrenciesUseCase(get()),
                    GetCurrencyValueUseCase(get()),
                    SearchCurrencyUseCase()
                )
            }
        }
    }
}
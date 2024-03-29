package com.sb.paypay.cc.presentation.di

import android.app.Application
import com.sb.paypay.cc.presentation.viewmodel.CurrencyViewModelFactory
import com.sb.paypay.cc.domain.usecase.GetAllCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideCurrencyViewModelFactory(
        application: Application,
        currencyUseCAse: GetAllCurrenciesUseCase
    ): CurrencyViewModelFactory {
        return CurrencyViewModelFactory(application, currencyUseCAse)
    }
}
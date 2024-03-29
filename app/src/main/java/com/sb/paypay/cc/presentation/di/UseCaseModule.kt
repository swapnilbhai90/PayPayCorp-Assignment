package com.sb.paypay.cc.presentation.di

import com.sb.paypay.cc.domain.repository.CurrenciesRepository
import com.sb.paypay.cc.domain.usecase.GetAllCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideCurrencyUseCase(repository: CurrenciesRepository): GetAllCurrenciesUseCase {
        return GetAllCurrenciesUseCase(repository)
    }
}
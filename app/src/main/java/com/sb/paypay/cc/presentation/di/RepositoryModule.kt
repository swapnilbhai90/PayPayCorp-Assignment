package com.sb.paypay.cc.presentation.di

import com.sb.paypay.cc.data.repository.CurrencyRepoImpl
import com.sb.paypay.cc.data.repository.dataSource.CurrencyDataSource
import com.sb.paypay.cc.domain.repository.CurrenciesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(currencyDataSource: CurrencyDataSource): CurrenciesRepository {

        return CurrencyRepoImpl(currencyDataSource)
    }
}
package com.sb.paypay.cc.presentation.di

import com.sb.paypay.cc.data.api.OerApiInterface
import com.sb.paypay.cc.data.repository.dataSource.CurrencyDataSource
import com.sb.paypay.cc.data.repository.dataSourceImpl.CurrenciesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCurrenciesDataSource(apiInterface: OerApiInterface): CurrencyDataSource {
        return CurrenciesRemoteDataSourceImpl(apiInterface)
    }
}
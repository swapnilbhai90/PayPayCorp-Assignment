package com.sb.paypay.cc.presentation.di

import com.sb.paypay.cc.data.api.OerApiInterface
import com.sb.paypay.cc.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyConverterApiService(retrofit: Retrofit):
            OerApiInterface {
        return retrofit.create(OerApiInterface::class.java)
    }

}
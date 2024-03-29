package com.sb.paypay.cc.data.api

import com.sb.paypay.cc.data.model.AllCurrencyConversionRates
import com.sb.paypay.cc.data.model.AllCurrenciesCountriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface OerApiInterface {

    @GET("/api/currencies.json")
    suspend fun getAllCurrencies(@Query("app_id") apiKey: String = ""): Response<AllCurrenciesCountriesResponse>

    @GET
    suspend fun getCurrenciesExchangeRates(@Url url: String): Response<AllCurrencyConversionRates>


}
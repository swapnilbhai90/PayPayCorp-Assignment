package com.sb.paypay.cc.data.repository.dataSource

import com.sb.paypay.cc.data.model.AllCurrencyConversionRates
import com.sb.paypay.cc.data.model.AllCurrenciesCountriesResponse
import retrofit2.Response

interface CurrencyDataSource {
    suspend fun  getAllCurrencies(): Response<AllCurrenciesCountriesResponse>

    suspend fun  getAllConvertedCurrencies(urlParam:String): Response<AllCurrencyConversionRates>
}
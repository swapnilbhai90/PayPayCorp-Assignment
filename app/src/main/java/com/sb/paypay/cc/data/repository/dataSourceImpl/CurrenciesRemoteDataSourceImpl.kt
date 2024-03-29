package com.sb.paypay.cc.data.repository.dataSourceImpl

import com.sb.paypay.cc.data.api.OerApiInterface
import com.sb.paypay.cc.data.model.AllCurrencyConversionRates
import com.sb.paypay.cc.data.model.AllCurrenciesCountriesResponse
import com.sb.paypay.cc.data.repository.dataSource.CurrencyDataSource
import retrofit2.Response

class CurrenciesRemoteDataSourceImpl(private val currencyApiService: OerApiInterface) :
    CurrencyDataSource {
    override suspend fun getAllCurrencies(): Response<AllCurrenciesCountriesResponse> {
        return currencyApiService.getAllCurrencies()
    }


    override suspend fun getAllConvertedCurrencies(urlParam: String): Response<AllCurrencyConversionRates> {
        //return  currencyApiService.getConvertedCurrencies("https://v6.exchangerate-api.com/v6/cb454bf2ff303899c1e08be3/latest/INR")
        return  currencyApiService.getCurrenciesExchangeRates(urlParam)
    }

}
package com.sb.paypay.cc.data.repository

import com.sb.paypay.cc.data.model.AllCurrencyConversionRates
import com.sb.paypay.cc.data.repository.ModelConverter.Companion.createCurrencyCodeCountryMap
import com.sb.paypay.cc.data.repository.dataSource.CurrencyDataSource
import com.sb.paypay.cc.data.model.AllCurrenciesCountriesResponse
import com.sb.paypay.cc.domain.repository.CurrenciesRepository
import com.sb.paypay.cc.utils.Constant
import com.sb.paypay.cc.utils.Resource
import retrofit2.Response

class CurrencyRepoImpl(private val currencyDataSource: CurrencyDataSource) : CurrenciesRepository {

    override suspend fun getAllCurrencies(): Resource<MutableMap<String, String>> {

      return responseToResource(currencyDataSource.getAllCurrencies())

    }

    override suspend fun getAllConvertedCurrencies(urlParam:String): Resource<MutableMap<String, String>> {
       return  responseToResourceAllCurrencies(currencyDataSource.getAllConvertedCurrencies(urlParam))
    }


    private fun responseToResource(response: Response<AllCurrenciesCountriesResponse>): Resource<MutableMap<String, String>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(createCurrencyCodeCountryMap(result))

            }

        }
        return  Resource.Error(response.message())

    }

    private fun responseToResourceAllCurrencies(response: Response<AllCurrencyConversionRates>): Resource<MutableMap<String, String>> {
        if(response.isSuccessful){
            response.body()?.let {result->
                if (result.code == "404"){
                    return Resource.Error(Constant.ERROR_MSG)
                }else
                return Resource.Success(createCurrencyCodeCountryMap(result.conversion_rates))

            }

        }
        return  Resource.Error(response.message())

    }



}
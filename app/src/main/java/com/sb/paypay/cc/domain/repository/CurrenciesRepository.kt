package com.sb.paypay.cc.domain.repository

import com.sb.paypay.cc.utils.Resource

interface CurrenciesRepository {

    suspend fun getAllCurrencies(): Resource<MutableMap<String, String>>
    suspend fun getAllConvertedCurrencies(urlParam: String): Resource<MutableMap<String, String>>

}
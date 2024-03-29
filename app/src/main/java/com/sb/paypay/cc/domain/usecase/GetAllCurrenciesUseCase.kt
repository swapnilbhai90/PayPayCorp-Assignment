package com.sb.paypay.cc.domain.usecase

import com.sb.paypay.cc.domain.repository.CurrenciesRepository
import com.sb.paypay.cc.utils.Resource

class GetAllCurrenciesUseCase(private val repository: CurrenciesRepository) {
    suspend fun execute(): Resource<MutableMap<String, String>> {
        return repository.getAllCurrencies()
    }
    suspend fun executeCurrencyConverter(urlParam: String): Resource<MutableMap<String, String>> {
        return repository.getAllConvertedCurrencies(urlParam)
    }


}
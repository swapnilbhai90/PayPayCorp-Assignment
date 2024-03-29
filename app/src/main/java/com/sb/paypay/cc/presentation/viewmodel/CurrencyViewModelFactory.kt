package com.sb.paypay.cc.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sb.paypay.cc.domain.usecase.GetAllCurrenciesUseCase

class CurrencyViewModelFactory(
    private val app: Application,
    private val currencyUseCase: GetAllCurrenciesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyConverterViewModel(app, currencyUseCase) as T
    }
}
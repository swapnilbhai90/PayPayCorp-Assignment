package com.sb.paypay.cc.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sb.paypay.cc.domain.usecase.GetAllCurrenciesUseCase
import com.sb.paypay.cc.utils.Constant.Companion.EXCHANGERATE_API_KEY
import com.sb.paypay.cc.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyConverterViewModel(
    val app: Application,
    private val currencyUseCase: GetAllCurrenciesUseCase
) : AndroidViewModel(app) {

    val selectedCountryCode = MutableLiveData<String>()
    val allCurrencies: MutableLiveData<Resource<MutableMap<String, String>>> = MutableLiveData()
    val allCurrenciesConvertedCurrencies: MutableLiveData<Resource<MutableMap<String, String>>> =
        MutableLiveData()

    fun getAllCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        allCurrencies.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult = currencyUseCase.execute()
                allCurrencies.postValue(apiResult)

            } else {
                allCurrencies.postValue(Resource.Error("Internet is not available"))
            }
        } catch (ex: Exception) {
            allCurrencies.postValue(Resource.Error(ex.message.toString()))
        }
    }

    fun getAllConvertedCurrencies() = viewModelScope.launch(Dispatchers.IO) {
        allCurrenciesConvertedCurrencies.postValue(Resource.Loading())
        try {
            if (isNetworkAvailable(app)) {
                val apiResult =
                    currencyUseCase.executeCurrencyConverter("https://v6.exchangerate-api.com/v6/$EXCHANGERATE_API_KEY/latest/${selectedCountryCode.value}")
                allCurrenciesConvertedCurrencies.postValue(apiResult)

            } else {
                allCurrenciesConvertedCurrencies.postValue(Resource.Error("Internet is not available"))
            }
        } catch (ex: Exception) {
            allCurrenciesConvertedCurrencies.postValue(Resource.Error(ex.message.toString()))
        }
    }


    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

}
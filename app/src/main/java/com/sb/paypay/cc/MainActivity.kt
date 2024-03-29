package com.sb.paypay.cc

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sb.paypay.cc.databinding.ActivityMainBinding
import com.sb.paypay.cc.presentation.adapter.CurrencyCountryNameAdapter
import com.sb.paypay.cc.presentation.adapter.CurrencySpinnerAdapter
import com.sb.paypay.cc.presentation.viewmodel.CurrencyConverterViewModel
import com.sb.paypay.cc.presentation.viewmodel.CurrencyViewModelFactory
import com.sb.paypay.cc.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var ccAdapter: CurrencyCountryNameAdapter
    private lateinit var binding: ActivityMainBinding
    private var currencyCountryCodeMutableList: MutableMap<String, String> = mutableMapOf()
    private var currencyCodeConvertedMap: MutableMap<String, String> = mutableMapOf()

    @Inject
    lateinit var factory: CurrencyViewModelFactory
    lateinit var viewModel: CurrencyConverterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[CurrencyConverterViewModel::class.java]
        getAllCurrency()
        initRecyclerview()
        setUpViewModel()
        initLoadImgButton()
    }

    private fun initLoadImgButton() {
        binding.imgLoadCurrencies.setOnClickListener { getAllCurrency() }
    }

    private fun initRecyclerview() {
        ccAdapter = CurrencyCountryNameAdapter(
            currencyCountryCodeMutableList,
            currencyCodeConvertedMap, binding.editTextText
        )
        binding.recyclerView.apply {
            adapter = ccAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

    }

    private fun getAllCurrency() {
        viewModel.getAllCurrencies()
    }

    private fun initCurrencyEditTextListener() {
        binding.editTextText.addTextChangedListener {
            MainScope().launch {
                ccAdapter.notifyDataSetChanged()

            }

        }
    }

    private fun initCurrencySpinner(currencyMutableMap: MutableMap<String, String>) {
        val customAdapter = CurrencySpinnerAdapter(this, currencyMutableMap)
        binding.spinner.adapter = customAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedCountryCurrencyCode = currencyMutableMap.entries.elementAt(position).key
                customAdapter.setSelectedText(selectedCountryCurrencyCode)
                viewModel.selectedCountryCode.value = selectedCountryCurrencyCode
                viewModel.getAllConvertedCurrencies()
                (view as TextView).setTextColor(Color.BLACK)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE

    }

    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE

    }

    private fun setUpViewModel() {
        viewModel.allCurrencies.observe(this) { response ->
            when (response) {
                is Resource.Error -> {
                    hideProgress()
                    binding.txtInternetMsg.visibility = View.VISIBLE
                    binding.imgLoadCurrencies.visibility = View.VISIBLE
                }

                is Resource.Loading -> {
                    showProgress()
                    binding.txtInternetMsg.visibility = View.GONE
                    binding.imgLoadCurrencies.visibility = View.GONE
                }

                is Resource.Success -> {
                    binding.txtInternetMsg.visibility = View.GONE
                    binding.imgLoadCurrencies.visibility = View.GONE
                    hideProgress()
                    response.data?.let {
                        currencyCountryCodeMutableList = it
                        ccAdapter.notifyDataSetChanged()
                        initCurrencySpinner(it)
                        initCurrencyEditTextListener()

                    }

                }

            }

        }

        viewModel.allCurrenciesConvertedCurrencies.observe(this@MainActivity) { response ->
            when (response) {
                is Resource.Error -> {
                    hideProgress()
                    currencyCodeConvertedMap.clear()
                    updateRecyclerViewCountryExchangeRates(currencyCodeConvertedMap)
                }

                is Resource.Loading -> showProgress()
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        updateRecyclerViewCountryExchangeRates(it)
                    }

                }

            }

        }

    }

    private fun updateRecyclerViewCountryExchangeRates(paramCurrencyCodeConvertedMap: MutableMap<String, String>) {
        ccAdapter = CurrencyCountryNameAdapter(
            currencyCountryCodeMutableList,
            paramCurrencyCodeConvertedMap, binding.editTextText
        )
        binding.recyclerView.adapter = ccAdapter
    }
}
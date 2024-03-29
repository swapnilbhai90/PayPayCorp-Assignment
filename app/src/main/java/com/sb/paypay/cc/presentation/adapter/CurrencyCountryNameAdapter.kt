package com.sb.paypay.cc.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

import com.sb.paypay.cc.databinding.CurrenciesNameItemBinding
import java.text.DecimalFormat

class CurrencyCountryNameAdapter(
    private val countryNameCodeMap: MutableMap<String, String>,
    private var currencyCodeConvertedMap: MutableMap<String, String>,
    private var userInputAmt: EditText

) : RecyclerView.Adapter<CurrencyCountryNameAdapter.CCViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CCViewHolder {
        val view = CurrenciesNameItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CCViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryNameCodeMap.size
    }


    override fun onBindViewHolder(holder: CCViewHolder, position: Int) {
        if (countryNameCodeMap != null) {
            if (countryNameCodeMap.isNotEmpty()) {
                val item = countryNameCodeMap.entries.elementAt(position)
                val currencyBaseValue = currencyCodeConvertedMap[item.key]
                val convertedExchangeValue =
                    getConvertedValues(currencyBaseValue, userInputAmt.text.toString())

                holder.bind(item.key, item.value, convertedExchangeValue)
            }
        }
    }

    private fun getConvertedValues(currencyBaseValue: String?, userInputAmt: String): String {
        if (currencyBaseValue.equals("null") || currencyBaseValue == null) {
            return "NA"
        } else if (userInputAmt.isEmpty()) {
            return "0"
        } else if (currencyBaseValue.isNotEmpty() && userInputAmt.isNotEmpty() && !userInputAmt.equals(".")) {
            return DecimalFormat("#,###.000").format(currencyBaseValue.toDouble() * userInputAmt.toDouble())
                .toString()
        }
        return "NA"
    }

    inner class CCViewHolder(val binding: CurrenciesNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(countryCode: String, countryName: String, exchangeRate: String) {
            binding.txtCountryCode.text = countryCode
            binding.txtCountryName.text = countryName
            binding.txtExchangeRate.text = exchangeRate
        }
    }
}
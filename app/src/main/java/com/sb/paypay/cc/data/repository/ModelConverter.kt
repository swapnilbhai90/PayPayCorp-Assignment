package com.sb.paypay.cc.data.repository

import com.google.gson.Gson

import com.sb.paypay.cc.data.model.AllCurrenciesCountriesResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


class ModelConverter {

    companion object {

        fun createCurrencyCodeCountryMap(response: AllCurrenciesCountriesResponse): MutableMap<String, String> {

            val json = Json.parseToJsonElement(Gson().toJson(response))
            val mutableMap = mutableMapOf<String, String>()
            if (json is JsonObject) {
                for ((key, value) in json) {
                    mutableMap[key] = value.toString().replace("\"", "")
                }
            } else {
                println("Not a valid JSON object.")
            }

            return mutableMap
        }
    }

}
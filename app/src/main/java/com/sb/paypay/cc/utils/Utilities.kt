package com.sb.paypay.cc.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.lang.NumberFormatException

object Utilities {

    fun validInput(text: String): Double {
        try {
            val num = java.lang.Double.parseDouble(text)
            if (num > 0) {
                return num
            }
        } catch (e: NumberFormatException) {
        }
        return 0.toDouble()
    }
}
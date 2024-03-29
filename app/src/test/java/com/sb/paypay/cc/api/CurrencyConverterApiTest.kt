package com.sb.paypay.cc.api

import com.google.common.truth.Truth.assertThat
import com.sb.paypay.cc.data.api.OerApiInterface
import com.sb.paypay.cc.domain.repository.CurrenciesRepository
import com.sb.paypay.cc.domain.usecase.GetAllCurrenciesUseCase
import com.sb.paypay.cc.presentation.viewmodel.CurrencyConverterViewModel
import com.sb.paypay.cc.utils.Utilities
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyConverterApiTest {

    private lateinit var service: OerApiInterface
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OerApiInterface::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getAllCurrenciesCountryCodeReceivedExpected() {
        runBlocking {
            enqueueMockResponse("CurrenciesResponse.json")
            val responseBody = service.getAllCurrencies().body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/api/currencies.json?app_id=")
        }
    }

    @Test
    fun getCurrenciesDataNotNull() {
        runBlocking {
            enqueueMockResponse("CurrenciesResponse.json")
            val responseBody = service.getAllCurrencies().body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(responseBody?.AED).isNotNull()
        }
    }

    @Test
    fun getCurrenciesResponseContent() {
        runBlocking {
            enqueueMockResponse("CurrenciesResponse.json")
            val responseBody = service.getAllCurrencies().body()
            assertThat(responseBody).isNotNull()
            assertThat(responseBody?.AED).isEqualTo("United Arab Emirates Dirham")
            assertThat(responseBody?.INR).isEqualTo("Indian Rupee")
        }
    }

    @Test
    fun  validInputAmountToDouble(){
        val result=Utilities.validInput("300")
        assertThat(result).isEqualTo(300.toDouble())
    }

    @Test
    fun  validInputAmountToLongDouble(){//no formater here used
        val result=Utilities.validInput("300.123456789")
        assertThat(result).isEqualTo(300.123456789)
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}
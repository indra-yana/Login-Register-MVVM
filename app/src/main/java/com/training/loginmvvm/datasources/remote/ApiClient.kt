package com.training.loginmvvm.datasources.remote

import com.training.loginmvvm.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.14
 * https://gitlab.com/indra-yana
 ****************************************************/

class ApiClient {

    companion object {
        //        private const val BASE_URL = "http://192.168.10.1:8002/api/"
        private const val BASE_URL = "http://192.168.100.8:8002/api/"
        private const val API_KEY = "your_secret_api_key";
    }

    fun <Api> buildApi(api: Class<Api>, authToken: String? = null): Api {
        // Request interceptor
        val requestInterceptor = Interceptor { chain ->
            val urlBuilder = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val requestHeaders = chain.request()
                .newBuilder()
                .url(urlBuilder)
                .addHeader("Authorization", "Bearer $authToken")
                .addHeader("Accept", "application/json")
                .build()

            return@Interceptor chain.proceed(requestHeaders)
        }

        // Add Logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        // OkHttpClient
        val okHttpClient = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }

                addInterceptor(requestInterceptor)
                connectTimeout(60, TimeUnit.SECONDS)
            }
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(api)
    }
}
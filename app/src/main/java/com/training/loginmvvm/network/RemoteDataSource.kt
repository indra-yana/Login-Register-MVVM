package com.training.loginmvvm.network

import com.training.loginmvvm.BuildConfig
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

class RemoteDataSource {

    companion object {
//        private const val BASE_URL = "http://192.168.10.1:8002/api/"
        private const val BASE_URL = "http://192.168.100.8:8002/api/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        // Add Logging interceptor
        val loggingInterceptor = OkHttpClient.Builder().also {
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                it.addInterceptor(logging)
            }
        }.connectTimeout(60, TimeUnit.SECONDS).build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(loggingInterceptor)
            .build()
            .create(api)
    }
}
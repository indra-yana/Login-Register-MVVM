package com.training.loginmvvm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.14
 * https://gitlab.com/indra-yana
 ****************************************************/

class RemoteDataSource {

    companion object {
        private const val BASE_URL = "http://simplifiedcoding.tech/mywebapp/public/api/"
    }

    fun <Api> buildApi(api: Class<Api>): Api {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }
}
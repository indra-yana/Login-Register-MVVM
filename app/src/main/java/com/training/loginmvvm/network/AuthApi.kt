package com.training.loginmvvm.network

import com.training.loginmvvm.responses.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.11
 * https://gitlab.com/indra-yana
 ****************************************************/

interface AuthApi {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): LoginResponse

}
package com.training.loginmvvm.network

import com.training.loginmvvm.responses.LoginResponse
import retrofit2.http.GET

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Monday, 18/01/2021 10.46
 * https://gitlab.com/indra-yana
 ****************************************************/

interface UserApi {

    @GET("user")
    suspend fun getUSer() : LoginResponse

}
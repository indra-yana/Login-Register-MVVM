package com.training.loginmvvm.repository

import com.training.loginmvvm.network.AuthApi

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.56
 * https://gitlab.com/indra-yana
 ****************************************************/

class AuthRepository(private val api: AuthApi) : BaseRepository() {

    suspend fun login(email: String, password: String) {
        safeApiCall {
            api.login(email, password)
        }
    }

}
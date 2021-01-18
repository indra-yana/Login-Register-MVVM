package com.training.loginmvvm.repository

import com.training.loginmvvm.network.AuthApi
import com.training.loginmvvm.network.Resource
import com.training.loginmvvm.responses.LoginResponse
import com.training.loginmvvm.utils.UserPreferences

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.56
 * https://gitlab.com/indra-yana
 ****************************************************/

class AuthRepository(private val api: AuthApi, private val preferences: UserPreferences) : BaseRepository() {

    suspend fun login(email: String, password: String) : Resource<LoginResponse> {
        return safeApiCall {
            api.login(email, password)
        }
    }

    suspend fun saveAuthToken(authToken: String) {
        preferences.saveAuthToken(authToken)
    }

}
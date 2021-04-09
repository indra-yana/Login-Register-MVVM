package com.training.loginmvvm.repositories

import com.training.loginmvvm.datasources.local.UserPreferences
import com.training.loginmvvm.datasources.remote.AuthApi
import com.training.loginmvvm.models.responses.ResponseStatus
import com.training.loginmvvm.models.responses.LoginResponse
import okhttp3.ResponseBody

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.56
 * https://gitlab.com/indra-yana
 ****************************************************/

class AuthRepository(private val api: AuthApi, private val preferences: UserPreferences) : BaseRepository() {

    suspend fun login(email: String, password: String) : ResponseStatus<LoginResponse> {
        return safeApiCall {
            api.login(email, password)
        }
    }

    suspend fun register(name: String, email: String, password: String, password2: String) : ResponseStatus<ResponseBody> {
        return safeApiCall {
            api.register(name, email, password, password2)
        }
    }

    suspend fun saveAuthToken(authToken: String) {
        preferences.saveAuthToken(authToken)
    }

}
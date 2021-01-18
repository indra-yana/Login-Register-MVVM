package com.training.loginmvvm.repository

import com.training.loginmvvm.network.AuthApi
import com.training.loginmvvm.network.Resource
import com.training.loginmvvm.network.UserApi
import com.training.loginmvvm.responses.LoginResponse
import com.training.loginmvvm.utils.UserPreferences

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.56
 * https://gitlab.com/indra-yana
 ****************************************************/

class UserRepository(private val api: UserApi) : BaseRepository() {

    suspend fun getUser() : Resource<LoginResponse> {
        return safeApiCall {
            api.getUSer()
        }
    }

}
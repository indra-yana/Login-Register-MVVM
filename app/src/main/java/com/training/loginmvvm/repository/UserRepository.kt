package com.training.loginmvvm.repository

import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.datasources.remote.UserApi
import com.training.loginmvvm.model.responses.LoginResponse

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
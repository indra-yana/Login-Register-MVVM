package com.training.loginmvvm.repositories

import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.datasources.remote.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.49
 * https://gitlab.com/indra-yana
 ****************************************************/

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        Resource.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        Resource.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun logout(api: UserApi) : Resource<ResponseBody> {
        return safeApiCall {
            api.logout()
        }
    }

}
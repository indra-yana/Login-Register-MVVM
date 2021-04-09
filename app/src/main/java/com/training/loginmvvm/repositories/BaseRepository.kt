package com.training.loginmvvm.repositories

import com.training.loginmvvm.models.responses.ResponseStatus
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

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseStatus<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseStatus.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ResponseStatus.Failure(false, throwable.code(), throwable.response()?.errorBody())
                    }
                    else -> {
                        ResponseStatus.Failure(true, null, null)
                    }
                }
            }
        }
    }

    suspend fun logout(api: UserApi) : ResponseStatus<ResponseBody> {
        return safeApiCall {
            api.logout()
        }
    }

}
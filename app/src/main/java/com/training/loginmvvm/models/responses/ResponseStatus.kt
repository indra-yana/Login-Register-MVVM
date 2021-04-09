package com.training.loginmvvm.models.responses

import okhttp3.ResponseBody

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 15.19
 * https://gitlab.com/indra-yana
 ****************************************************/

sealed class ResponseStatus<out T> {

    data class Success<out T>(val value: T) : ResponseStatus<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResponseStatus<Nothing>()
    object Loading : ResponseStatus<Nothing>()
}
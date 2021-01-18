package com.training.loginmvvm.viewmodel

import androidx.lifecycle.ViewModel
import com.training.loginmvvm.datasources.remote.UserApi
import com.training.loginmvvm.repository.BaseRepository

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Monday, 18/01/2021 16.37
 * https://gitlab.com/indra-yana
 ****************************************************/

abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    suspend fun logout(api: UserApi) {
        repository.logout(api)
    }
}
package com.training.loginmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.training.loginmvvm.models.responses.ResponseStatus
import com.training.loginmvvm.repositories.UserRepository
import com.training.loginmvvm.models.responses.LoginResponse
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Monday, 18/01/2021 10.50
 * https://gitlab.com/indra-yana
 ****************************************************/

class HomeViewModel(private val repository: UserRepository) : BaseViewModel(repository) {

    private val _user: MutableLiveData<ResponseStatus<LoginResponse>> = MutableLiveData()
    val user: LiveData<ResponseStatus<LoginResponse>> get() = _user

    fun getUser () {
        viewModelScope.launch {
            _user.value = ResponseStatus.Loading
            _user.value = repository.getUser()
        }
    }
}

package com.training.loginmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.loginmvvm.network.Resource
import com.training.loginmvvm.repository.AuthRepository
import com.training.loginmvvm.responses.LoginResponse
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.40
 * https://gitlab.com/indra-yana
 ****************************************************/

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = repository.login(email, password)
        }
    }

    fun saveAuthToken(authToken: String) {
        viewModelScope.launch {
            repository.saveAuthToken(authToken)
        }
    }

}
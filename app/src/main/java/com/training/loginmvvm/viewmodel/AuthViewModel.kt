package com.training.loginmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.models.responses.LoginResponse
import com.training.loginmvvm.repositories.AuthRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.40
 * https://gitlab.com/indra-yana
 ****************************************************/

class AuthViewModel(private val repository: AuthRepository) : BaseViewModel(repository) {

    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    private val _registerResponse : MutableLiveData<Resource<ResponseBody>> = MutableLiveData()
    val registerResponse: LiveData<Resource<ResponseBody>> get() = _registerResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResponse.value = Resource.Loading
            _loginResponse.value = repository.login(email, password)
        }
    }

    fun register(name: String, email: String, password: String, password2: String) {
        viewModelScope.launch {
            _registerResponse.value = Resource.Loading
            _registerResponse.value = repository.register(name, email, password, password2)
        }
    }

    suspend fun saveAuthToken(authToken: String) {
        repository.saveAuthToken(authToken)
    }

}
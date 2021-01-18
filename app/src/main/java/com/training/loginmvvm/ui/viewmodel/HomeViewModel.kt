package com.training.loginmvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.loginmvvm.network.Resource
import com.training.loginmvvm.repository.UserRepository
import com.training.loginmvvm.responses.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Monday, 18/01/2021 10.50
 * https://gitlab.com/indra-yana
 ****************************************************/

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>> get() = _user

    fun getUser () {
        viewModelScope.launch {
            _user.value = Resource.Loading
            _user.value = repository.getUser()
        }
    }
}

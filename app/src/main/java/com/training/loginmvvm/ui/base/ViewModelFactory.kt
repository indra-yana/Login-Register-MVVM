package com.training.loginmvvm.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.training.loginmvvm.repository.AuthRepository
import com.training.loginmvvm.repository.BaseRepository
import com.training.loginmvvm.ui.viewmodel.AuthViewModel
import java.lang.IllegalArgumentException

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.53
 * https://gitlab.com/indra-yana
 ****************************************************/

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found!")
        }
    }

}
package com.training.loginmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.training.loginmvvm.databinding.FragmentLoginBinding
import com.training.loginmvvm.network.AuthApi
import com.training.loginmvvm.repository.AuthRepository
import com.training.loginmvvm.ui.base.BaseFragment
import com.training.loginmvvm.ui.viewmodel.AuthViewModel

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): AuthRepository {
        return AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))
    }

}
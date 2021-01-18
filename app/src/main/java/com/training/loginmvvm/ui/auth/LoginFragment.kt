package com.training.loginmvvm.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.training.loginmvvm.databinding.FragmentLoginBinding
import com.training.loginmvvm.datasources.remote.AuthApi
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.repository.AuthRepository
import com.training.loginmvvm.ui.base.BaseFragment
import com.training.loginmvvm.ui.viewmodel.AuthViewModel
import com.training.loginmvvm.utils.*

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            viewBinding.pbLoading.visible(false)
            when(it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.user.access_token!!)
                    Toast.makeText(requireContext(), "Login Successfully!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewBinding.etPassword.addTextChangedListener {
            val email = viewBinding.etEmail.text.toString().trim()
            viewBinding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        viewBinding.btnLogin.enable(false)
        viewBinding.btnLogin.setOnClickListener {
            val email = viewBinding.etEmail.text.toString().trim()
            val password = viewBinding.etPassword.text.toString().trim()

            // TODO: add input validation

            viewBinding.pbLoading.visible(true)
            viewModel.login(email, password)
        }

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
        return AuthRepository(apiClient.buildApi(AuthApi::class.java), userPreferences)
    }

//    fun getRandomString(length: Int) : String {
//        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//        return (1..length)
//            .map { charset.random() }
//            .joinToString("")
//    }
}
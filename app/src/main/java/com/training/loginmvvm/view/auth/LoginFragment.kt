package com.training.loginmvvm.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.training.loginmvvm.databinding.FragmentLoginBinding
import com.training.loginmvvm.datasources.remote.AuthApi
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.repositories.AuthRepository
import com.training.loginmvvm.view.BaseFragment
import com.training.loginmvvm.utils.handleApiError
import com.training.loginmvvm.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            viewBinding.pbLoading.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.user.access_token!!)
                        Toast.makeText(requireContext(), "Login Successfully!", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Failure -> {
                    handleApiError(it, retry = { login() })
                }
            }
        })

        viewBinding.etPassword.addTextChangedListener {
            val email = viewBinding.etEmail.text.toString().trim()
            viewBinding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        viewBinding.btnLogin.enable(false)
        viewBinding.btnLogin.setOnClickListener {
            login()
        }

        viewBinding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            requireView().findNavController().navigate(action)
        }

        viewBinding.ivShowHidePassword.showOrHidePassword(viewBinding.etPassword)

    }

    private fun login() {
        val email = viewBinding.etEmail.text.toString().trim()
        val password = viewBinding.etPassword.text.toString().trim()

        // TODO: add input validation
        viewModel.login(email, password)
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
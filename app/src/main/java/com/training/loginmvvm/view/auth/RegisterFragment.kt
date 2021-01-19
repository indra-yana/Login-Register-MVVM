package com.training.loginmvvm.view.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.training.loginmvvm.databinding.FragmentRegisterBinding
import com.training.loginmvvm.datasources.remote.AuthApi
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.repositories.AuthRepository
import com.training.loginmvvm.utils.enable
import com.training.loginmvvm.utils.handleApiError
import com.training.loginmvvm.utils.showOrHidePassword
import com.training.loginmvvm.utils.visible
import com.training.loginmvvm.view.BaseFragment
import com.training.loginmvvm.viewmodel.AuthViewModel


class RegisterFragment : BaseFragment<AuthViewModel, FragmentRegisterBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            viewBinding.pbLoading.visible(it is Resource.Loading)
            viewBinding.btnRegister.enable(it !is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.value}", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    handleApiError(it, retry = { register() })
                }
            }
        })

        with(viewBinding) {
            ivShowHidePassword.showOrHidePassword(etPassword, etPassword2)

            btnRegister.setOnClickListener { register() }
        }
    }

    private fun register() {
        with(viewBinding) {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val password2 = etPassword2.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Please Enter the name!", Toast.LENGTH_SHORT).show()
                return
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Please Enter Valid Email address!", Toast.LENGTH_SHORT).show()
                return
            }

            if (password.length < 6) {
                Toast.makeText(requireContext(), "The password must be at least 6 characters in length!", Toast.LENGTH_SHORT).show()
                return
            }

            if (password != password2) {
                Toast.makeText(requireContext(), "The password you have entered doesn't match!", Toast.LENGTH_SHORT).show()
                return
            }

            viewModel.register(name, email, password, password2)
        }
    }

    override fun getViewModel(): Class<AuthViewModel> {
        return AuthViewModel::class.java
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): AuthRepository {
        return AuthRepository(apiClient.buildApi(AuthApi::class.java), userPreferences)
    }


}
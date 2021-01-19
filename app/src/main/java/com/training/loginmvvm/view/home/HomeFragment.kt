package com.training.loginmvvm.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.training.loginmvvm.databinding.FragmentHomeBinding
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.datasources.remote.UserApi
import com.training.loginmvvm.repositories.UserRepository
import com.training.loginmvvm.models.User
import com.training.loginmvvm.view.BaseFragment
import com.training.loginmvvm.viewmodel.HomeViewModel
import com.training.loginmvvm.utils.handleApiError
import com.training.loginmvvm.utils.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, UserRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewBinding.pbLoading.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    updateUI(it.value.user)
                }
                is Resource.Failure -> {
                    handleApiError(it, retry = { getUser() })
                }
            }
        })

        viewBinding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun getUser() {
        viewModel.getUser()
    }

    private fun updateUI(user: User) {
        with(viewBinding) {
            tvID.text = user.id.toString()
            tvName.text = user.name
            tvEmail.text = user.email
        }
    }

    override fun getViewModel(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun getRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }

        return UserRepository(apiClient.buildApi(UserApi::class.java, token))
    }

}
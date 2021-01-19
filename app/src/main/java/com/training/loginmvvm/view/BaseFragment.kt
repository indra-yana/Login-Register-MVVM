package com.training.loginmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.training.loginmvvm.datasources.remote.ApiClient
import com.training.loginmvvm.datasources.remote.UserApi
import com.training.loginmvvm.repositories.BaseRepository
import com.training.loginmvvm.view.auth.AuthActivity
import com.training.loginmvvm.viewmodel.BaseViewModel
import com.training.loginmvvm.viewmodel.ViewModelFactory
import com.training.loginmvvm.datasources.local.UserPreferences
import com.training.loginmvvm.utils.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.26
 * https://gitlab.com/indra-yana
 ****************************************************/

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding, BR : BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var viewModel: VM
    protected lateinit var viewBinding: VB
    protected val apiClient = ApiClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences.getInstance(requireContext()) // UserPreferences(requireContext())
        viewBinding = getViewBinding(inflater, container)
        viewModel = ViewModelProvider(this, ViewModelFactory(getRepository())).get(getViewModel())

        lifecycleScope.launch {
            userPreferences.authToken.first()
        }

        return viewBinding.root
    }

    fun logout() {
        lifecycleScope.launch {
            val authToken = userPreferences.authToken.first()
            val api = apiClient.buildApi(UserApi::class.java, authToken)

            viewModel.logout(api)
            userPreferences.clearAuthToken()
            requireActivity().startNewActivity(AuthActivity::class.java)
        }
    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getRepository(): BR

}
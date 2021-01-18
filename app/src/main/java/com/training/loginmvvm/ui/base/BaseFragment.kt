package com.training.loginmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.training.loginmvvm.datasources.remote.ApiClient
import com.training.loginmvvm.repository.BaseRepository
import com.training.loginmvvm.ui.viewmodel.ViewModelFactory
import com.training.loginmvvm.utils.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.26
 * https://gitlab.com/indra-yana
 ****************************************************/

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding, BR : BaseRepository> : Fragment() {

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
        viewModel = ViewModelProvider(this,
            ViewModelFactory(getRepository())
        ).get(getViewModel())

        lifecycleScope.launch {
            userPreferences.authToken.first()
        }

        return viewBinding.root
    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getRepository(): BR

}
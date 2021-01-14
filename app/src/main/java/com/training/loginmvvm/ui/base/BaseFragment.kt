package com.training.loginmvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.training.loginmvvm.repository.BaseRepository

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Thursday, 14/01/2021 16.26
 * https://gitlab.com/indra-yana
 ****************************************************/

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding, BR : BaseRepository> : Fragment() {

    protected lateinit var vieBinding : VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vieBinding = getViewBinding(inflater, container)

        return vieBinding.root
    }

    abstract fun getViewModel(): Class<VM>
    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) : VB
    abstract fun getRepository(): BR

}
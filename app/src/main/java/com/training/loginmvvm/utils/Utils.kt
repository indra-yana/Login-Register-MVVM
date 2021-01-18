package com.training.loginmvvm.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.training.loginmvvm.datasources.remote.Resource
import com.training.loginmvvm.ui.auth.LoginFragment
import com.training.loginmvvm.ui.BaseFragment

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Saturday, 16/01/2021 13.08
 * https://gitlab.com/indra-yana
 ****************************************************/

fun<A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(this)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun String.getRandomString(length: Int) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random().toString().substring(0, length)
}

fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)

    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }

    snackBar.show()
}

fun Fragment.handleApiError(failure: Resource.Failure, retry: (() -> Unit)? = null) {
    when {
        failure.isNetworkError -> {
            requireView().snackBar("Please check your internet connection!", retry)
        }
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackBar("You've entered incorrect email or password!")
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        failure.errorCode == 404 -> {
            requireView().snackBar("The page you're looking couldn't be find!")
        }
        failure.errorCode == 500 -> {
            requireView().snackBar("Internal server error!")
        }
        else -> {
            requireView().snackBar(failure.errorBody?.string().toString())
        }
    }
}

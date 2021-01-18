package com.training.loginmvvm.utils

import android.app.Activity
import android.content.Intent
import android.view.View

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

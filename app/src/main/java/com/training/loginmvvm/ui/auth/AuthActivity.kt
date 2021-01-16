package com.training.loginmvvm.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.training.loginmvvm.R
import com.training.loginmvvm.utils.UserPreferences

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        UserPreferences.getInstance(this).authToken.asLiveData().observe(this, Observer {
            Log.d("Token", it ?: "Token is NULL")
        })
    }
}
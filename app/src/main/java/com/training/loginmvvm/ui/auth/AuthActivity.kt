package com.training.loginmvvm.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.training.loginmvvm.R
import com.training.loginmvvm.ui.home.HomeActivity
import com.training.loginmvvm.datasources.local.UserPreferences
import com.training.loginmvvm.utils.startNewActivity

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        UserPreferences.getInstance(this).authToken.asLiveData().observe(this, Observer {
            if (it != null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startNewActivity( HomeActivity::class.java)
                }, 600)
            }
        })
    }
}
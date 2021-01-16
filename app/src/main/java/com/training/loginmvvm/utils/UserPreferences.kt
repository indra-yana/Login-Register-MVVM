package com.training.loginmvvm.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Friday, 15/01/2021 19.40
 * https://gitlab.com/indra-yana
 ****************************************************/

class UserPreferences(context: Context) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")

        @Volatile
        private var instance: UserPreferences? = null

        @Synchronized
        fun getInstance(context: Context) : UserPreferences {
            return instance ?: UserPreferences(context).also { instance = it }
        }
    }

    init {
        dataStore = applicationContext.createDataStore(name = "data_store")
    }

    val authToken: Flow<String?> get() = dataStore.data.map { it[KEY_AUTH] }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit {
            it[KEY_AUTH] = authToken
        }
    }

}
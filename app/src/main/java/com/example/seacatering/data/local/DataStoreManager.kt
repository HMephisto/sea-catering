package com.example.seacatering.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class DataStoreManager (private val context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")

    companion object {
        val KEY_USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveUserId(userId: String){
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = userId
        }
    }

    val getUserId: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[KEY_USER_ID] }
}
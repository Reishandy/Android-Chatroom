package com.reishandy.chatroom.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedPreferenceManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveRefreshTokens(refreshToken: String) {
        encryptedPrefs.edit()
            .putString(REFRESH_TOKEN, refreshToken)
            .apply()
    }

    fun saveAccessToken(accessToken: String) {
        encryptedPrefs.edit()
            .putString(ACCESS_TOKEN, accessToken)
            .apply()
    }

    fun saveUserId(userId: String) {
        encryptedPrefs.edit()
            .putString(USER_ID, userId)
            .apply()
    }

    fun getAccessToken(): String? = encryptedPrefs.getString(ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = encryptedPrefs.getString(REFRESH_TOKEN, null)

    fun getUserId(): String? = encryptedPrefs.getString(USER_ID, null)

    fun clearAll() {
        encryptedPrefs.edit().clear().apply()
    }

    companion object {
        private const val PREF_NAME = "AppPreferences"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN = "access_token"
        private const val USER_ID = "user_id"
    }
}
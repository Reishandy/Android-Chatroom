package com.reishandy.chatroom.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reishandy.chatroom.data.EncryptedPreferenceManager
import com.reishandy.chatroom.model.account.ChangeViewModel
import com.reishandy.chatroom.model.account.LoginViewModel
import com.reishandy.chatroom.model.account.RegisterViewModel
import com.reishandy.chatroom.model.account.VerifyViewModel
import com.reishandy.chatroom.network.ApiServiceRepository

class ViewModelFactory(
    private val application: Application,
    private val apiRepository: ApiServiceRepository = ApiServiceRepository(),
    private val prefManager: EncryptedPreferenceManager = EncryptedPreferenceManager(application)
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(application, apiRepository, prefManager) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(application, apiRepository) as T
            }
            modelClass.isAssignableFrom(VerifyViewModel::class.java) -> {
                VerifyViewModel(application, apiRepository) as T
            }
            modelClass.isAssignableFrom(ChangeViewModel::class.java) -> {
                ChangeViewModel(application, apiRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
package com.reishandy.chatroom.model.auth

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.reishandy.chatroom.data.LoginUiState
import com.reishandy.chatroom.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Text Field
    var email: String by mutableStateOf("")
        private set

    var password: String by mutableStateOf("")
        private set

    fun updateEmail(email: String) {
        this.email = email
        validateEmail()
    }

    fun updatePassword(password: String) {
        this.password = password
        validatePassword()
    }

    fun clearFields() {
        email = ""
        password = ""
    }

    // Main Flow
    fun login(): Boolean {
        if (!validateEmail() or !validatePassword()) return false

        // TODO: Implement login logic

        return true
    }

    // Helper
    private fun validateEmail(): Boolean {
        val isEmailValid: Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        _uiState.update {
            it.copy(
                emailLabel = if (!isEmailValid) R.string.email_error_validation else R.string.email,
                isEmailError = !isEmailValid
            )
        }

        return isEmailValid
    }

    private fun validatePassword(): Boolean {
        val isPasswordValid: Boolean = password.length >= 8

        _uiState.update {
            it.copy(
                passwordLabel = if (!isPasswordValid) R.string.password_error_validation else R.string.password,
                isPasswordError = !isPasswordValid
            )
        }

        return isPasswordValid
    }
}
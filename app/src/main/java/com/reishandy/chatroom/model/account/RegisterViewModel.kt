package com.reishandy.chatroom.model.account

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import com.reishandy.chatroom.model.RegisterUiState
import com.reishandy.chatroom.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState: MutableStateFlow<RegisterUiState> = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    // Text Field
    var username: String by mutableStateOf("")
        private set

    var email: String by mutableStateOf("")
        private set

    var password: String by mutableStateOf("")
        private set

    var confirmPassword: String by mutableStateOf("")
        private set

    fun updateUsername(username: String) {
        this.username = username
        validateUsername()
    }

    fun updateEmail(email: String) {
        this.email = email
        validateEmail()
    }

    fun updatePassword(password: String) {
        this.password = password
        validatePassword()
    }

    fun updateConfirmPassword(confirmPassword: String) {
        this.confirmPassword = confirmPassword
    }

    fun clearFields() {
        username = ""
        email = ""
        password = ""
        confirmPassword = ""
    }

    // Main Flow
    fun register(): Boolean {
        if (!validateUsername() or !validateEmail() or !validatePassword() or !validateConfirmPassword()) return false

        // TODO: Implement register logic

        return true
    }

    // Helper
    private fun validateUsername(): Boolean {
        val isUsernameValid: Boolean = username.length >= 3

        _uiState.update {
            it.copy(
                usernameLabel = if (!isUsernameValid) R.string.username_error_validation else R.string.username,
                isUsernameError = !isUsernameValid
            )
        }

        return isUsernameValid
    }

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
        val isPasswordValid: Boolean = password.length in 8..32

        _uiState.update {
            it.copy(
                passwordLabel = if (!isPasswordValid) R.string.password_error_validation else R.string.password,
                isPasswordError = !isPasswordValid
            )
        }

        return isPasswordValid
    }

    private fun validateConfirmPassword(): Boolean {
        val isConfirmPasswordValid: Boolean = password == confirmPassword

        _uiState.update {
            it.copy(
                confirmPasswordLabel = if (!isConfirmPasswordValid) R.string.password_error_not_match else R.string.confirm_password,
                isConfirmPasswordError = !isConfirmPasswordValid
            )
        }

        return isConfirmPasswordValid
    }
}
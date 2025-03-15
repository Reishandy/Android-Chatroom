package com.reishandy.chatroom.model.account

import android.app.Application
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.reishandy.chatroom.R
import com.reishandy.chatroom.data.ApiResponseStatus
import com.reishandy.chatroom.data.ApiResponseWrapper
import com.reishandy.chatroom.data.RegisterUiState
import com.reishandy.chatroom.network.ApiServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    application: Application,
    private val apiRepository: ApiServiceRepository = ApiServiceRepository()
) : AndroidViewModel(application) {
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
    suspend fun register(): Boolean {
        if (!validateUsername() or !validateEmail() or !validatePassword() or !validateConfirmPassword()) return false

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val response: ApiResponseWrapper<String> = apiRepository.register(email, password, username)

        when (response.status) {
            ApiResponseStatus.SUCCESS -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                return true
            }

            ApiResponseStatus.EMAIL_ERROR -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        emailLabel = R.string.email_error_registered,
                        isEmailError = true
                    )
                }
                return false
            }

            ApiResponseStatus.ERROR -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        generalError = response.error
                    )
                }
                return false
            }

            else -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                return false
            }
        }
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
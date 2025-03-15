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
import com.reishandy.chatroom.data.EncryptedPreferenceManager
import com.reishandy.chatroom.data.LoginResponse
import com.reishandy.chatroom.data.LoginUiState
import com.reishandy.chatroom.network.ApiServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(
    application: Application,
    private val apiRepository: ApiServiceRepository = ApiServiceRepository(),
    private val prefManager: EncryptedPreferenceManager = EncryptedPreferenceManager(application)
) : AndroidViewModel(application) {
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
    // TODO: DEBUG REMOVE
    fun getUID(): String {
        return prefManager.getUserId()?.toString() ?: "null"
    }

    suspend fun login(): Boolean {
        if (!validateEmail() or !validatePassword()) return false

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val response: ApiResponseWrapper<LoginResponse> = apiRepository.login(email, password)

        when (response.status) {
            ApiResponseStatus.SUCCESS -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }

                // Store tokens and user id in shared preferences (encrypted)
                prefManager.saveRefreshTokens(response.response?.tokens!!.refresh_token)
                prefManager.saveAccessToken(response.response.tokens.access_token)
                prefManager.saveUserId(response.response.user_id)

                return true
            }

            ApiResponseStatus.EMAIL_ERROR -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        emailLabel = R.string.email_error_not_found,
                        isEmailError = true
                    )
                }
                return false
            }

            ApiResponseStatus.PASSWORD_ERROR -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        passwordLabel = R.string.password_error_invalid,
                        isPasswordError = true
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
                        generalError = response.error
                    )
                }
                return false
            }
        }
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
        val isPasswordValid: Boolean = password.length in 8..32

        _uiState.update {
            it.copy(
                passwordLabel = if (!isPasswordValid) R.string.password_error_validation else R.string.password,
                isPasswordError = !isPasswordValid
            )
        }

        return isPasswordValid
    }
}
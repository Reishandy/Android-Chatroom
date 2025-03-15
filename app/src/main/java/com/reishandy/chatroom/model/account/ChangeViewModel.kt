package com.reishandy.chatroom.model.account

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.reishandy.chatroom.R
import com.reishandy.chatroom.data.ChangeUiState
import com.reishandy.chatroom.network.ApiServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChangeViewModel(
    application: Application,
    private val apiRepository: ApiServiceRepository = ApiServiceRepository()
) : AndroidViewModel(application) {
    private val _uiState: MutableStateFlow<ChangeUiState> = MutableStateFlow(ChangeUiState())
    val uiState: StateFlow<ChangeUiState> = _uiState.asStateFlow()

    // Text Field
    var username: String by mutableStateOf("")
        private set

    var oldPassword: String by mutableStateOf("")
        private set

    var newPassword: String by mutableStateOf("")
        private set

    var confirmPassword: String by mutableStateOf("")
        private set

    fun updateUsername(username: String) {
        this.username = username
        validateUsername()
    }

    fun updateOldPassword(password: String) {
        this.oldPassword = password
        validateOldPassword()
    }

    fun updateNewPassword(newPassword: String) {
        this.newPassword = newPassword
        validateNewPassword()
    }

    fun updateConfirmPassword(confirmPassword: String) {
        this.confirmPassword = confirmPassword
    }

    fun clearFields() {
        username = ""
        oldPassword = ""
        confirmPassword = ""
    }

    // Main Flow
    fun showUsernameForm() {
        _uiState.value = ChangeUiState(isUsernameFormVisible = true)
    }

    fun hideUsernameForm() {
        _uiState.value = ChangeUiState(isUsernameFormVisible = false)
    }

    fun showPasswordForm() {
        _uiState.value = ChangeUiState(isPasswordFormVisible = true)
    }

    fun hidePasswordForm() {
        _uiState.value = ChangeUiState(isPasswordFormVisible = false)
    }

    fun changeUsername(): Boolean {
        if (!validateUsername()) return false

        // TODO: Implement change username logic

        return true
    }

    fun changePassword(): Boolean {
        if (!validateOldPassword() or !validateNewPassword() or !validateConfirmPassword()) return false

        // TODO: Implement change password logic

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

    private fun validateOldPassword(): Boolean {
        val isPasswordValid: Boolean = oldPassword.length in 8..32

        _uiState.update {
            it.copy(
                oldPasswordLabel = if (!isPasswordValid) R.string.password_error_validation else R.string.password,
                isOldPasswordError = !isPasswordValid
            )
        }

        return isPasswordValid
    }

    private fun validateNewPassword(): Boolean {
        val isNewPasswordValid: Boolean = newPassword.length in 8..32

        _uiState.update {
            it.copy(
                newPasswordLabel = if (!isNewPasswordValid) R.string.password_error_validation else R.string.new_password,
                isNewPasswordError = !isNewPasswordValid
            )
        }

        return isNewPasswordValid
    }

    private fun validateConfirmPassword(): Boolean {
        val isConfirmPasswordValid: Boolean = newPassword == confirmPassword

        _uiState.update {
            it.copy(
                confirmPasswordLabel = if (!isConfirmPasswordValid) R.string.password_error_not_match else R.string.confirm_password,
                isConfirmPasswordError = !isConfirmPasswordValid
            )
        }

        return isConfirmPasswordValid
    }
}
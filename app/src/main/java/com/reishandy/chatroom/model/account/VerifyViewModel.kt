package com.reishandy.chatroom.model.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import com.reishandy.chatroom.model.VerifyUiState
import com.reishandy.chatroom.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VerifyViewModel(application: Application) : AndroidViewModel(application) {
    val _uiState: MutableStateFlow<VerifyUiState> = MutableStateFlow(VerifyUiState())
    val uiState: StateFlow<VerifyUiState> = _uiState.asStateFlow()

    // Text Field
    var verificationCode: String by mutableStateOf("")
        private set

    fun updateVerificationCode(verificationCode: String) {
        this.verificationCode = verificationCode
        validateVerificationCode()
    }

    fun clearFields() {
        verificationCode = ""
    }

    // Main Flow
    fun updateMailSentTo(mailSentTo: String) {
        _uiState.update {
            it.copy(mailSentTo = mailSentTo)
        }
    }

    fun verify(): Boolean {
        if (!validateVerificationCode()) return false

        // TODO: Implement verification logic

        return true
    }

    fun resendVerificationCode() {
        // TODO: Implement resend verification code logic

        return
    }

    // Helper
    private fun validateVerificationCode(): Boolean {
        val isVerificationCodeValid: Boolean =
            verificationCode.length == 6 && verificationCode.all { it.isDigit() }

        _uiState.update {
            it.copy(
                verificationCodeLabel = if (!isVerificationCodeValid) R.string.verification_code_error_validation else R.string.verification_code,
                isVerificationCodeError = !isVerificationCodeValid
            )
        }

        return isVerificationCodeValid
    }
}
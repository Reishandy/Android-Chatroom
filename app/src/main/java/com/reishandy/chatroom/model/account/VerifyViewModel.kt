package com.reishandy.chatroom.model.account

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.reishandy.chatroom.R
import com.reishandy.chatroom.data.ApiResponseStatus
import com.reishandy.chatroom.data.ApiResponseWrapper
import com.reishandy.chatroom.data.VerifyUiState
import com.reishandy.chatroom.network.ApiServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VerifyViewModel(
    application: Application,
    private val apiRepository: ApiServiceRepository = ApiServiceRepository()
) : AndroidViewModel(application) {
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

    suspend fun verify(): Boolean {
        if (!validateVerificationCode()) return false

        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        val response: ApiResponseWrapper<String> =
            apiRepository.verify(_uiState.value.mailSentTo, verificationCode)

        when (response.status) {
            ApiResponseStatus.SUCCESS -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                return true
            }

            ApiResponseStatus.VERIFICATION_CODE_ERROR -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        verificationCodeLabel = R.string.verification_code_error_invalid,
                        isVerificationCodeError = true
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
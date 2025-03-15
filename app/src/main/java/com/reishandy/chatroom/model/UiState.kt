package com.reishandy.chatroom.model

import androidx.annotation.StringRes
import com.reishandy.chatroom.R

data class LoginUiState(
    @StringRes val emailLabel: Int = R.string.email,
    val isEmailError: Boolean = false,
    @StringRes val passwordLabel: Int = R.string.password,
    val isPasswordError: Boolean = false,
)

data class RegisterUiState(
    @StringRes val usernameLabel: Int = R.string.username,
    val isUsernameError: Boolean = false,
    @StringRes val emailLabel: Int = R.string.email,
    val isEmailError: Boolean = false,
    @StringRes val passwordLabel: Int = R.string.password,
    val isPasswordError: Boolean = false,
    @StringRes val confirmPasswordLabel: Int = R.string.confirm_password,
    val isConfirmPasswordError: Boolean = false,
)

data class VerifyUiState(
    val mailSentTo: String = "",
    @StringRes val verificationCodeLabel: Int = R.string.verification_code,
    val isVerificationCodeError: Boolean = false,
)

data class ChangeUiState(
    val isUsernameFormVisible: Boolean = false,
    val isPasswordFormVisible: Boolean = false,
    @StringRes val usernameLabel: Int = R.string.username,
    val isUsernameError: Boolean = false,
    @StringRes val oldPasswordLabel: Int = R.string.password,
    val isOldPasswordError: Boolean = false,
    @StringRes val newPasswordLabel: Int = R.string.new_password,
    val isNewPasswordError: Boolean = false,
    @StringRes val confirmPasswordLabel: Int = R.string.confirm_password,
    val isConfirmPasswordError: Boolean = false,
)
package com.reishandy.chatroom.data

// Body
data class RegisterBody(
    val email: String,
    val password: String,
    val username: String
)

data class VerifyBody(
    val email: String,
    val code: String
)

data class LoginBody(
    val email: String,
    val password: String
)

// Response
data class ErrorResponse(
    val message: String
)

data class Response(
    val message: String
)

data class TokenResponse(
    val access_token: String,
    val type: String
)

data class TokensResponse(
    val access_token: String,
    val refresh_token: String,
    val type: String
)

@Suppress("PropertyName", "PropertyName", "PropertyName", "PropertyName", "PropertyName")
data class LoginResponse(
    val message: String,
    val tokens: TokensResponse,
    val user_id: String
)

// Repository response
data class ApiResponseWrapper<T>(
    val status: ApiResponseStatus,
    val response: T ?= null,
    val error: String ?= null
)

enum class ApiResponseStatus {
    SUCCESS,
    EMAIL_ERROR,
    PASSWORD_ERROR,
    VERIFICATION_CODE_ERROR,
    ERROR
}

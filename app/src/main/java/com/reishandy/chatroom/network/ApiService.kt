package com.reishandy.chatroom.network

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/register")
    suspend fun register(@Body body: RegisterBody): Response

    @POST("/verify")
    suspend fun verify(@Body body: VerifyBody): Response

    @POST("/login")
    suspend fun login(@Body body: LoginBody): LoginResponse
}

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

data class TokensResponse(
    val access_token: String,
    val refresh_token: String,
    val type: String
)

data class LoginResponse(
    val message: String,
    val tokens: TokensResponse,
    val user_id: String
)
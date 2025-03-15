package com.reishandy.chatroom.network

import com.reishandy.chatroom.data.LoginBody
import com.reishandy.chatroom.data.LoginResponse
import com.reishandy.chatroom.data.RegisterBody
import com.reishandy.chatroom.data.Response
import com.reishandy.chatroom.data.VerifyBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("/register")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun register(@Body body: RegisterBody): Response

    @POST("/verify")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun verify(@Body body: VerifyBody): Response

    @POST("/login")
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    suspend fun login(@Body body: LoginBody): LoginResponse
}
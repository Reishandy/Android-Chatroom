package com.reishandy.chatroom.network

import android.util.Log
import com.google.gson.Gson
import com.reishandy.chatroom.data.ApiResponseStatus
import com.reishandy.chatroom.data.ApiResponseWrapper
import com.reishandy.chatroom.data.ErrorResponse
import com.reishandy.chatroom.data.LoginBody
import com.reishandy.chatroom.data.RegisterBody
import com.reishandy.chatroom.data.TokensResponse
import com.reishandy.chatroom.data.VerifyBody
import com.reishandy.chatroom.factory.RetrofitFactory
import java.io.IOException

class ApiServiceRepository {
    private val baseUrl = "https://chatroom.reishandy.my.id"
    private val apiService = RetrofitFactory.getInstance(baseUrl).create(ApiService::class.java)

    suspend fun register(
        email: String,
        password: String,
        username: String
    ): ApiResponseWrapper<String> {
        return try {
            val body = RegisterBody(email, password, username)
            val response = apiService.register(body)

            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = response.message
            )
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

            when (e.code()) {
                400, 409 -> ApiResponseWrapper(
                    status = ApiResponseStatus.EMAIL_ERROR,
                    error = errorResponse.message
                )

                500 -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = errorResponse.message
                )

                else -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Unknown error occurred"
                )
            }
        } catch (e: IOException) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Network error occurred"
            )
        }
    }

    suspend fun verify(email: String, code: String): ApiResponseWrapper<String> {
        return try {
            val body = VerifyBody(email, code)
            val response = apiService.verify(body)
            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = response.message
            )
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

            when (e.code()) {
                400, 404 -> ApiResponseWrapper(
                    status = ApiResponseStatus.VERIFICATION_CODE_ERROR,
                    error = errorResponse.message
                )

                500 -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = errorResponse.message
                )

                else -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Unknown error occurred"
                )
            }
        } catch (e: IOException) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Network error occurred"
            )
        }
    }

    suspend fun login(email: String, password: String): ApiResponseWrapper<TokensResponse> {
        return try {
            val body = LoginBody(email, password)
            val response = apiService.login(body)
            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = TokensResponse(
                    response.tokens.access_token,
                    response.tokens.refresh_token,
                    response.tokens.type
                )
            )
        } catch (e: retrofit2.HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

            when (e.code()) {
                404 -> ApiResponseWrapper(
                    status = ApiResponseStatus.EMAIL_ERROR,
                    error = errorResponse.message
                )
                400 -> ApiResponseWrapper(
                    status = ApiResponseStatus.PASSWORD_ERROR,
                    error = errorResponse.message
                )
                500 -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = errorResponse.message
                )
                else -> ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Unknown error occurred"
                )
            }
        } catch (e: IOException) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Network error occurred"
            )
        }
    }
}
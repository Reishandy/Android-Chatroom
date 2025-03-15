package com.reishandy.chatroom.network

import com.google.gson.Gson
import com.reishandy.chatroom.data.ApiResponseStatus
import com.reishandy.chatroom.data.ApiResponseWrapper
import com.reishandy.chatroom.data.ErrorResponse
import com.reishandy.chatroom.data.LoginBody
import com.reishandy.chatroom.data.LoginResponse
import com.reishandy.chatroom.data.RegisterBody
import com.reishandy.chatroom.data.Response
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
            val body: RegisterBody = RegisterBody(email, password, username)
            val response: Response = apiService.register(body)

            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = response.message
            )
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 530) {
                return ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Cloudflare server down"
                )
            }

            val errorBody: String? = e.response()?.errorBody()?.string()
            val errorResponse: ErrorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

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
                error = "Network error occurred, check your internet connection"
            )
        } catch (e: Exception) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Unknown error occurred: ${e.message}"
            )
        }
    }

    suspend fun verify(email: String, code: String): ApiResponseWrapper<String> {
        return try {
            val body: VerifyBody = VerifyBody(email, code)
            val response: Response = apiService.verify(body)

            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = response.message
            )
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 530) {
                return ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Cloudflare server down"
                )
            }

            val errorBody: String? = e.response()?.errorBody()?.string()
            val errorResponse: ErrorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

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
                error = "Network error occurred, check your internet connection"
            )
        } catch (e: Exception) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Unknown error occurred: ${e.message}"
            )
        }
    }

    suspend fun login(email: String, password: String): ApiResponseWrapper<LoginResponse> {
        return try {
            val body: LoginBody = LoginBody(email, password)
            val response: LoginResponse = apiService.login(body)

            ApiResponseWrapper(
                status = ApiResponseStatus.SUCCESS,
                response = LoginResponse(
                    message = response.message,
                    tokens = TokensResponse(
                        access_token = response.tokens.access_token,
                        refresh_token = response.tokens.refresh_token,
                        type = response.tokens.type
                    ),
                    user_id = response.user_id
                )
            )
        } catch (e: retrofit2.HttpException) {
            if (e.code() == 530) {
                return ApiResponseWrapper(
                    status = ApiResponseStatus.ERROR,
                    error = "Cloudflare server down"
                )
            }

            val errorBody: String? = e.response()?.errorBody()?.string()
            val errorResponse: ErrorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)

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
                error = "Network error occurred, check your internet connection"
            )
        } catch (e: Exception) {
            ApiResponseWrapper(
                status = ApiResponseStatus.ERROR,
                error = "Unknown error occurred: ${e.message}"
            )
        }
    }
}
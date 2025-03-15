package com.reishandy.chatroom.network

import com.google.gson.Gson
import com.reishandy.chatroom.factory.RetrofitFactory
import java.io.IOException

class ApiServiceRepository {
    private val baseUrl = "https://chatroom.reishandy.my.id"
    private val apiService = RetrofitFactory.getInstance(baseUrl).create(ApiService::class.java)

}
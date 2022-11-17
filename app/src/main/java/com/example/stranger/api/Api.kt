package com.example.stranger.api

import com.example.stranger.model.response.Music
import com.example.stranger.model.request.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("chart-realtime")
    suspend fun getBxhZing(): Response<Music>

    @POST("/login")
    suspend fun login(@Body user: User): Response<User>

}
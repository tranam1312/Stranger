package com.example.stranger.api

import com.example.stranger.model.Music
import com.example.stranger.model.Song
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("chart-realtime")
    suspend fun getBxhZing(): Response<Music>
}
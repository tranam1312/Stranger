package com.example.stranger.di

import android.content.Context
import com.example.stranger.api.Api
import com.example.stranger.local.Preferences
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mp3.zing.vn/xhr/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").setLenient().create()
                )
            )
            .build()
    }

    @Provides
    fun provideMDLService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

}
package com.example.stranger.di

import com.example.stranger.api.Api
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stranger-93947-default-rtdb.asia-southeast1.firebasedatabase.app/")
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
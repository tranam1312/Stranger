package com.example.stranger

import android.app.Application
import com.google.firebase.FirebaseApp.initializeApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application  : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}
package com.example.stranger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.findNavController
import com.example.stranger.R
import com.example.stranger.base.BaseActivity
import com.example.stranger.databinding.ActivitySplashBinding
import com.example.stranger.service.PostService
import com.example.stranger.ui.wellcom.WellcomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayout(): Int = R.layout.activity_splash

    override fun init() {
     replaceFragmentNoBack(WellcomeFragment.newInstance())
    }


}
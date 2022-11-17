package com.example.stranger.ui

import android.content.Intent
import com.example.stranger.R
import com.example.stranger.base.BaseActivity
import com.example.stranger.databinding.ActivitySplashBinding
import com.example.stranger.model.response.Song
import com.example.stranger.service.MusicService
import com.example.stranger.ui.main.MainFragment
import com.example.stranger.ui.wellcom.WellcomeFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override fun getLayout(): Int = R.layout.activity_splash
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun init() {
        if (!auth.currentUser?.uid.isNullOrEmpty()) {
            replaceFragment(MainFragment.newInstance(), MainFragment::class.java.name)
        } else {
            addFragment(WellcomeFragment.newInstance(), WellcomeFragment::class.java.name)
        }
    }
    fun startServiceMusic(listSong: ArrayList<Song>, position: Int) {
        val intent = Intent(this, MusicService::class.java)
        intent.putExtra("listSong", listSong)
        intent.putExtra("position", position)
        startService(intent)
    }
}
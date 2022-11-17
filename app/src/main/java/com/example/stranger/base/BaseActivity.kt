package com.example.stranger.base

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.extension.hideKeyboard
import com.example.stranger.local.Preferences
import com.example.stranger.ui.main.MainFragment


abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    private var exit: Int = 0
    protected val preferences: Preferences by lazy {
        Preferences.getInstance(applicationContext)
    }
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.root.setOnClickListener {
            hideKeyboard()
        }
        init()
    }

    abstract fun getLayout(): Int
    abstract fun init()


    fun addFragment(fragment: Fragment, className: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(className)
            .add(R.id.fragment, fragment).commit()
    }

    fun replaceFragment(fragment: Fragment, className: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .addToBackStack(className)
            .replace(R.id.fragment, fragment).commit()
    }

    fun replaceFragmentNoBack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            .replace(R.id.fragment, fragment).commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
        if (fragment is MainFragment) {
            exit++
            if (exit == 2) {
                finish()
            } else {
                Toast.makeText(this, "Nhấp back lần nữa để thoát", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    exit = 0
                }, 3000)
            }
        } else super.onBackPressed()
    }
}
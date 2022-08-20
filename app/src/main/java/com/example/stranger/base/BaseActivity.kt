package com.example.stranger.base

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.stranger.R
import com.example.stranger.local.Preferences

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    private var exit: Int = 0
    protected val preferences: Preferences by lazy {
        Preferences.getInstance(applicationContext)
    }
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        init()
    }

    abstract fun getLayout(): Int
    abstract fun init()


    fun addFragment(fragment: Fragment, className: String) {
        Log.e("alo", supportFragmentManager.backStackEntryCount.toString())
        supportFragmentManager.beginTransaction().addToBackStack(className)
            .add(R.id.fragment, fragment).commit()
    }

    fun replaceFragment(fragment: Fragment, className: String) {
        Log.e("alo", supportFragmentManager.backStackEntryCount.toString())
        supportFragmentManager.beginTransaction().addToBackStack(className)
            .replace(R.id.fragment, fragment).commit()
    }

    fun replaceFragmentNoBack(fragment: Fragment) {
        Log.e("alo", supportFragmentManager.backStackEntryCount.toString())
        supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit()
    }

    fun removeFragment() {
        Handler().postDelayed({
        Log.e("alo", supportFragmentManager.backStackEntryCount.toString())
        if (supportFragmentManager.backStackEntryCount > 0) {
            Log.e("alo", supportFragmentManager.fragments.toString())
            var listFragment = supportFragmentManager.fragments
            for (i in listFragment.size - 2 downTo 0) {
                supportFragmentManager.fragments.removeAt(i)
            }
            supportFragmentManager.beginTransaction().commitAllowingStateLoss()
        }
        supportFragmentManager.executePendingTransactions()

        Log.e("alo", supportFragmentManager.fragments.toString())

        },500L)
    }

    override fun onBackPressed() {
        Log.e("alo", supportFragmentManager.backStackEntryCount.toString())
        Log.e("alo", supportFragmentManager.fragments.toString())
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            exit++
            if (exit == 2) {
                finish()
            } else {
                Toast.makeText(this, "Nhấp back lần nữa để thoát", Toast.LENGTH_SHORT).show()
                Handler().postDelayed({
                    exit = 0
                }, 3000)
            }
        }

    }
}
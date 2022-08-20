package com.example.stranger.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stranger.local.Preferences
import com.example.stranger.ui.SplashActivity


abstract class BaseFragment : Fragment() {
    protected lateinit var splashActivity: SplashActivity
    protected val preferences: Preferences by lazy {
        Preferences.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashActivity = activity as SplashActivity
        setToolBar(splashActivity)
        init()
        initAction()
    }

    open fun setToolBar(activity: SplashActivity) {

    }

    abstract fun init()
    abstract fun initAction()

    fun finish() {
        requireActivity().finish()
    }


}
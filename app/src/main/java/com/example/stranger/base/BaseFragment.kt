package com.example.stranger.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.stranger.local.Preferences
import com.example.stranger.ui.Dialog.OpenLibraryDialog
import com.example.stranger.ui.SplashActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        initData()
        initAction()
    }

    open fun setToolBar(activity: AppCompatActivity) {

    }

    abstract fun init()
    abstract fun initData()
    abstract fun initAction()


    fun finish() {
        requireActivity().finish()
    }

    fun addFragment(fragment: Fragment, className: String) =
        splashActivity.addFragment(fragment, className)

    fun replaceFragment(fragment: Fragment, className: String) =
        splashActivity.replaceFragment(fragment, className)

    fun replaceFragmentNoBack(fragment: Fragment) = splashActivity.replaceFragmentNoBack(fragment)

    fun onBackPressed() {
        splashActivity.onBackPressed()
    }

    fun openDialog(onClickOpenLibraryDialog: ((Int) -> Unit)? = null) {
        fragmentManager?.let {
            val fragment = OpenLibraryDialog.newInstance(onClickOpenLibraryDialog)
            it.beginTransaction().add(fragment, fragment.tag).commit()
        }
    }

}
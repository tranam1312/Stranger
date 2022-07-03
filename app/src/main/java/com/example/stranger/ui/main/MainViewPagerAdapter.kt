package com.example.stranger.ui.main

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.stranger.ui.home.HomeFragment
import com.example.stranger.ui.message.MessageFragment
import com.example.stranger.ui.music.MusicFragment
import com.example.stranger.ui.notification.NotificationFragment
import com.example.stranger.ui.setting.SettingFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fragmentManager, behavior) {
    override fun getCount(): Int {
        return 5
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return HomeFragment.newInstance()
            1 -> return MusicFragment.newInstance()
            2 -> return MessageFragment.newInstance()
            3 -> return NotificationFragment.newInstance()
            4 -> return SettingFragment.newInstance()
            else -> return HomeFragment.newInstance()
        }
    }

}
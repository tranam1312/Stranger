package com.example.stranger.ui.main

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentMainBinding
import com.example.stranger.ui.home.HomeViewModel
import com.example.stranger.ui.message.MessageViewModel
import com.example.stranger.ui.notification.NotificationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragmentWithBinding<FragmentMainBinding>() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private lateinit var viewPager: ViewPager
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter
    private lateinit var bottomNavigationView: BottomNavigationView

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val messageViewModel: MessageViewModel by activityViewModels()
    private val notificationViewModel: NotificationViewModel by activityViewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentMainBinding =
        FragmentMainBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
        }

    override fun init() {
        binding.toolbar.setTitle("Trang chủ")
        binding.toolbar.setIconRight(R.drawable.ic_search)
        viewPager = binding.viewPager
        mainViewPagerAdapter = MainViewPagerAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager.offscreenPageLimit = 4
        bottomNavigationView = binding.bottomNavigationView
        viewPager.adapter = mainViewPagerAdapter
        setItemSelect()
        setPageSelect()
        homeViewModel.updateItemHomeSize.observe(viewLifecycleOwner) {
            var homeBader = binding.bottomNavigationView.getOrCreateBadge(R.id.home)
            if (it == 0) {
                homeBader.isVisible = false
                homeBader.clearNumber()
            } else {
                homeBader?.isVisible = true
                homeBader?.number = it
            }
        }
    }

    override fun initData() {

    }

    fun setItemSelect() {
        bottomNavigationView.setOnItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.home -> viewPager.currentItem = 0
                    R.id.music -> viewPager.currentItem = 1
                    R.id.message -> viewPager.currentItem = 2
                    R.id.notification -> viewPager.currentItem = 3
                    R.id.setting -> viewPager.currentItem = 4
                }
                return false
            }
        })
    }

    fun setPageSelect() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> {
                        binding.toolbar.setTitle("Trang chủ")
                        bottomNavigationView.menu.findItem(R.id.home).isChecked = true
                    }
                    1 -> {
                        binding.toolbar.setTitle("Nghe nhạc")
                        bottomNavigationView.menu.findItem(R.id.music).isChecked = true
                    }
                    2 -> {
                        binding.toolbar.setTitle("Tin nhắn")
                        bottomNavigationView.menu.findItem(R.id.message).isChecked = true

                    }
                    3 -> {
                        binding.toolbar.setTitle("Thông báo")
                        bottomNavigationView.menu.findItem(R.id.notification).isChecked = true

                    }
                    4 -> {
                        binding.toolbar.setTitle("Cài đặt")
                        bottomNavigationView.menu.findItem(R.id.setting).isChecked = true
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

    }

    override fun initAction() {

    }
}
package com.example.stranger.ui.home

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.activityViewModels
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.ui.home.comment.CommentFragment
import com.example.stranger.ui.home.post.PostFragment
import com.example.stranger.ui.home.searchHome.SearchHomeFragment
import com.example.stranger.ui.main.MainFragment
import com.example.stranger.ui.setting.profile.ProFileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithBinding<FragmentHomeBinding>() {
    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
        const val OPEN_POST = "OPEN_POST"
        const val OPEN_PROFILE = "OPEN_PROFILE"
        const val OPEN_COMMENT = "OPEN_COMMENT"
    }

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var homeAdapter: HomeAdapter
    private val mainFragment: MainFragment = MainFragment.newInstance()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.search -> {
            addFragment(
                SearchHomeFragment.newInstance(),
                SearchHomeFragment::class.java.name
            )
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
            homeAdapter = HomeAdapter(viewModel,openFragment)
            rvHome.adapter = homeAdapter
        }

    private val openFragment: (String, String?) -> Unit = { openFragment, key ->
        when (openFragment) {
            OPEN_POST -> splashActivity.addFragment(
                PostFragment.newInstance(),
                PostFragment::class.java.name
            )
            OPEN_PROFILE -> splashActivity.addFragment(
                ProFileFragment.newInstance(),
                ProFileFragment::class.java.name
            )
            OPEN_COMMENT -> splashActivity.addFragment(
                CommentFragment.newInstance(key),
                CommentFragment::class.java.name
            )
        }
    }

    override fun init() {
        binding.newpost.viewmodel = viewModel
        viewModel.getDataHomeSever()
        getDataHome()
    }

    override fun initData() {

    }

    private fun getDataHome() {
        viewModel.listItemHome.observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = false
            Log.e("alo", it.toString())
            homeAdapter.submitList(it)
        }

        viewModel.proFile.observe(viewLifecycleOwner) {
            binding.newpost.profile = it
        }
    }

    override fun initAction() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            Handler().postDelayed({
                viewModel.getDataHomeSever()
            }, 1000)
        }

        binding.newpost.text.setOnClickListener {
            openFragment.invoke(OPEN_POST, null)
        }
    }

}
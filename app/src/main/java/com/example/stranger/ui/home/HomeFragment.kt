package com.example.stranger.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.post.PostFragment
import com.example.stranger.ui.home.searchHome.SearchHomeFragment
import com.example.stranger.ui.setting.profile.ProFileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithBinding<FragmentHomeBinding>() {

    companion object {
        fun newInstance() : HomeFragment{
            return HomeFragment()
        }
        const val OPEN_POST = "OPEN_POST"
        const val OPEN_PROFILE ="OPEN_PROFILE"
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeAdapter : HomeAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.search -> {
            splashActivity.addFragment(SearchHomeFragment.newInstance(), SearchHomeFragment::class.java.name)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater).apply {
            viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
            homeAdapter = HomeAdapter(viewModel, openFragment)
            this.lifecycleOwner = viewLifecycleOwner
            rvHome.setHasFixedSize(true)
            rvHome.adapter = homeAdapter
        }

    private val openFragment : (String) -> Unit = {
        when(it){
            OPEN_POST -> splashActivity.addFragment(PostFragment.newInstance(), PostFragment::class.java.name)
            OPEN_PROFILE ->  splashActivity.addFragment(ProFileFragment.newInstance(), ProFileFragment::class.java.name)
        }
    }

    override fun init() {
        getDataHome()
    }
    private fun getDataHome(){
        viewModel.listItemHome.observe(viewLifecycleOwner){
            Log.e("hello",it.toString())
            homeAdapter.submitList(it)
        }
    }

    override fun initAction() {

    }

}
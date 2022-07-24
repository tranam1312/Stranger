package com.example.stranger.ui.home

import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithBinding<FragmentHomeBinding>() {

    companion object {
        fun newInstance() : HomeFragment{
            return HomeFragment()
        }
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var homeAdapter : HomeAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.search -> {
            findNavController().navigate(R.id.action_mainFragment_to_searchHomeFragment)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater).apply {
            viewModel = ViewModelProvider(this@HomeFragment)[HomeViewModel::class.java]
            homeAdapter = HomeAdapter(viewModel)
            this.lifecycleOwner = viewLifecycleOwner
            rvHome.setHasFixedSize(true)
            rvHome.adapter = homeAdapter
        }

    override fun init() {

    }

    override fun initAction() {

    }

}
package com.example.stranger.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragmentWithBinding<FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter = HomeAdapter(viewModel)

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
            rvHome.setHasFixedSize(true)
            rvHome.adapter = homeAdapter
        }

    override fun init() {

    }



    override fun initAction() {

    }

}
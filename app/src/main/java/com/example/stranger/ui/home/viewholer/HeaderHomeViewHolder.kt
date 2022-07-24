package com.example.stranger.ui.home.viewholer

import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemHaderHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.model.ProFile
import com.example.stranger.ui.home.HomeViewModel

class HeaderHomeViewHolder(
    private val homeViewModel: HomeViewModel,
    val binding: ViewDataBinding
) :
    BaseViewHolder<ItemHome, ViewDataBinding>(binding) {
    fun binding() {
        if (binding is ItemHaderHomeBinding) {
            binding.viewmodel = homeViewModel
            binding.lifecycleOwner?.let {
                homeViewModel.proFile.observe(it) {
                    binding.profile = it
                }

            }

        }
    }
}
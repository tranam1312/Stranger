package com.example.stranger.ui.home.viewholer

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemHaderHomeBinding
import com.example.stranger.databinding.ItemPostBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.HomeViewModel

class HomeViewHolder (private val homeViewModel: HomeViewModel,private val binding: ViewDataBinding) :BaseViewHolder<ItemHome,ViewDataBinding>(binding) {
    fun binding() {
        if (binding is ItemPostBinding ){

        }
        }
}
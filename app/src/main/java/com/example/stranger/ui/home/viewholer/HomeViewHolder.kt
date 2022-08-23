package com.example.stranger.ui.home.viewholer

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemHaderHomeBinding
import com.example.stranger.databinding.ItemHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.HomeViewModel

class HomeViewHolder (private val homeViewModel: HomeViewModel,private val binding: ViewDataBinding) :BaseViewHolder<ItemHome,ViewDataBinding>(binding) {
    override fun bind(itemData: ItemHome) {
        if (binding is ItemHomeBinding){
            binding.item = itemData
            Log.e("hello", itemData.toString())
            Glide.with(binding.root).load(if (!itemData?.urlImage.isNullOrEmpty())itemData?.urlImage else R.drawable.af)
                .into(binding.imageView)

        }
        }
}
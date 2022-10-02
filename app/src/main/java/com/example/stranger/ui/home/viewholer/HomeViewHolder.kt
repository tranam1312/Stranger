package com.example.stranger.ui.home.viewholer

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.common.State
import com.example.stranger.databinding.ItemHomeBinding
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.HomeFragment.Companion.OPEN_COMMENT
import com.example.stranger.ui.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewHolder(
    private val homeViewModel: HomeViewModel,
    private val binding: ViewDataBinding,
    private val openFragment: (String, String?) -> Unit?
) : BaseViewHolder<ItemHome, ViewDataBinding>(binding) {
    override fun bind(itemData: ItemHome) {
        super.bind(itemData)
        if (binding is ItemHomeBinding) {
            getPoFileUid(itemData.userid, binding)
            binding.item = itemData
            initView(binding)
            openComment(binding)
            onClickLike(binding)
        }
    }


    private fun getPoFileUid(uid: String?, binding: ItemHomeBinding) {
        homeViewModel.viewModelScope.launch {
            uid?.let {
                homeViewModel.repository.getProFile(it).collect { proFile ->
                    when (proFile) {
                        is State.Loading -> {

                        }
                        is State.Success -> proFile.data?.let {
                            withContext(Dispatchers.Default) {
                                binding.profile = it
                            }
                            this.coroutineContext.job.cancel()
                        }
                    }
                }
            }
        }
    }


    private fun initView(binding: ItemHomeBinding) {
        val userLike = itemData?.listUserLike?.filter {
            it == homeViewModel.proFile.value?.key
        } ?: arrayListOf()

        Glide.with(binding.root)
            .load(if (userLike.isNotEmpty()) R.drawable.ic_heart_enable else R.drawable.ic_heart)
            .into(binding.like)
    }


    private fun openComment(binding: ItemHomeBinding) {
        binding?.itemView?.setOnClickListener {
            openFragment.invoke(OPEN_COMMENT, itemData?.key.toString())
        }
    }

    private fun onClickLike(binding: ItemHomeBinding) {
        binding?.like?.setOnClickListener {
            val list = itemData?.listUserLike?.filter {
                it == homeViewModel.proFile.value?.key
            } ?: arrayListOf()
            if (list.isNotEmpty()) itemData?.listUserLike?.removeAll(list.toSet())
            else homeViewModel.getUid()?.let { it1 ->
                itemData?.listUserLike?.add(it1)
            }
            Glide.with(binding.root)
                .load(if (list.isEmpty()) R.drawable.ic_heart_enable else R.drawable.ic_heart)
                .into(binding.like)

            itemData?.let { it1 -> homeViewModel.updateItemHome(it1) }
        }
    }
}



package com.example.stranger.ui.home

import android.view.ViewGroup
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseDiffUtilItemCallback
import com.example.stranger.base.recyclerview.BaseRecyclerAdapter
import com.example.stranger.model.ItemHome
import com.example.stranger.ui.home.viewholer.HomeViewHolder

class HomeAdapter(
    private val viewModel: HomeViewModel, private val openFragment: (String,String?) -> Unit
) : BaseRecyclerAdapter<ItemHome, HomeViewHolder>(ItemDiffUtilCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewHolder {
        return HomeViewHolder(
            viewModel,
            getViewHolderDataBinding(parent, viewType)
        ,openFragment)
    }

    override fun getItemLayoutResource(viewType: Int): Int = R.layout.item_home

    class ItemDiffUtilCallback : BaseDiffUtilItemCallback<ItemHome>() {
        override fun areItemsTheSame(oldItem: ItemHome, newItem: ItemHome): Boolean {
            return oldItem.key == newItem.key
        }
    }
}
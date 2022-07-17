package com.example.stranger.base.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : Any, VB : ViewDataBinding>(
    private val binding: VB
) : RecyclerView.ViewHolder(binding.root) {

    private var itemData: T? = null
        private set

    init {
        itemView.setOnClickListener {
            itemData?.let(::onItemClickListener)
        }
    }

    open fun bind(itemData: T) {
        this.itemData = itemData
    }

    open fun onItemClickListener(itemData: T){

    }
}

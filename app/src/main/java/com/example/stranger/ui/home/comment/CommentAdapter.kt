/*
 *  Created by Trần Nam on 8/27/22, 5:04 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 8/27/22, 5:04 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.ui.home.comment

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseDiffUtilItemCallback
import com.example.stranger.base.recyclerview.BaseRecyclerAdapter
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemCommentBinding
import com.example.stranger.model.Comment

class CommentAdapter : BaseRecyclerAdapter<Comment, CommentAdapter.CommentViewHolder>(DiffCallBack()) {
    class DiffCallBack : BaseDiffUtilItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

    }

    inner class CommentViewHolder(private val binding: ViewDataBinding) :
        BaseViewHolder<Comment, ViewDataBinding>(binding) {
        override fun bind(itemData: Comment) {
            super.bind(itemData)
            if (binding is ItemCommentBinding) {
                binding.comment = itemData
                binding.showReplycomment
            }
        }
    }

    override fun getItemLayoutResource(viewType: Int): Int = R.layout.item_comment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = getViewHolderDataBinding(parent, viewType)
        return CommentViewHolder(binding)
    }
}
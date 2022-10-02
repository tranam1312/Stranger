/*
 *  Created by Trần Nam on 9/1/22, 11:08 PM
 *    tranhoainam1312@gmail.com
 *     Last modified 9/1/22, 11:08 PM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.ui.home.comment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseDiffUtilItemCallback
import com.example.stranger.base.recyclerview.BaseRecyclerAdapter
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.model.ReplyComment

class ReplyCommentAdapter :
    BaseRecyclerAdapter<ReplyComment, ReplyCommentAdapter.ReplyCommentViewHolder>(DiffCallBack()) {

    class DiffCallBack : BaseDiffUtilItemCallback<ReplyComment>() {
        override fun areItemsTheSame(oldItem: ReplyComment, newItem: ReplyComment): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ReplyComment, newItem: ReplyComment): Boolean {
            return oldItem.key == oldItem.key
        }
    }

    inner class ReplyCommentViewHolder(private val binding: ViewDataBinding) :
        BaseViewHolder<ReplyComment, ViewDataBinding>(binding) {

    }

    override fun getItemLayoutResource(viewType: Int): Int = R.layout.item_reply_comment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyCommentViewHolder {
        return ReplyCommentViewHolder(getViewHolderDataBinding(parent, viewType))
    }
}
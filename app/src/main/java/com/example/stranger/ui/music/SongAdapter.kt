package com.example.stranger.ui.music

import android.view.ViewGroup
import com.example.stranger.R
import com.example.stranger.base.recyclerview.BaseDiffUtilItemCallback
import com.example.stranger.base.recyclerview.BaseRecyclerAdapter
import com.example.stranger.model.Song

class SongAdapter( private val onClickSong : (Song)-> Unit) : BaseRecyclerAdapter<Song, SongViewHolder>(ItemDiffUtilCallback()) {

    class ItemDiffUtilCallback : BaseDiffUtilItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun getItemLayoutResource(viewType: Int): Int = R.layout.item_song

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(getViewHolderDataBinding(parent, viewType), onClickSong)
    }
}
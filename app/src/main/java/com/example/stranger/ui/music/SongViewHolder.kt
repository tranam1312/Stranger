package com.example.stranger.ui.music

import androidx.databinding.ViewDataBinding
import com.example.stranger.base.recyclerview.BaseViewHolder
import com.example.stranger.databinding.ItemSongBinding
import com.example.stranger.model.response.Song

class SongViewHolder(private val binding: ViewDataBinding, private val onClickSong : (Int)-> Unit) :
    BaseViewHolder<Song, ViewDataBinding>(binding) {
    override fun bind(itemData: Song) {
        super.bind(itemData)
        if (binding is ItemSongBinding){
            binding.song = itemData
            onItemClickListener{
                onClickSong.invoke(position)
            }
        }
    }
}
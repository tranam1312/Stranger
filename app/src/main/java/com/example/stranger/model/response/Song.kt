package com.example.stranger.model.response

import java.io.Serializable

data class Song(
    val id: String? =null,
    val name: String? =null,
    val title: String? =null,
    val code: String? =null,
    val artists_names: String? =null,
    val isoffical: String? =null,
    val isWorldWide: String? =null,
    val playlist_id: String? =null,
    val type: String? =null,
    val lyric: String? = null,
    val thumbnail: String? = null,
    val performer: String? = null
) : Serializable

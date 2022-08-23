package com.example.stranger.model

import com.google.gson.annotations.SerializedName

data class ItemHome(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("keyShare")
    var keyShare: String? = null,
    @SerializedName("keyShare")
    val userid: String? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("urlImage")
    var urlImage: String? = null,
    @SerializedName("listUserLike")
    var listUserLike: ArrayList<String>? = null,
    @SerializedName("listCommnent")
    var listCommnent: HashMap<String, Comment>? = null,
    @SerializedName("datetime")
    val datetime: Long? = null
)
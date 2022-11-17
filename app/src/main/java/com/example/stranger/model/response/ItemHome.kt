package com.example.stranger.model.response

import com.example.stranger.model.response.Comment
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
    var listUserLike: ArrayList<String>? = arrayListOf(),
    @SerializedName("listCommnent")
    var listCommnent: HashMap<String, Comment>? = hashMapOf(),
    @SerializedName("datetime")
    val datetime: Long? = null
)
package com.example.stranger.model

data class ItemHome(
    val key: String? = null,
    var keyShare: String? = null,
    val userid: String? = null,
    val content: String? = null,
    var urlImage: String? = null,
    var listUserLike: ArrayList<String>? = null,
    var listCommnent: HashMap<String, Comment>? = null,
    val datetime: String? = null
)
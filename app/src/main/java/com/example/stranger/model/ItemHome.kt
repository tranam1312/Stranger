    package com.example.stranger.model

data class ItemHome(
    val key: String = "",
    var keyShare: String? ="",
    val userid: String ="",
    val userName: String = "",
    val content: String? = "",
    var urlList: ArrayList<String>? = arrayListOf(),
    var listUserLike: ArrayList<String>? = arrayListOf(),
    var listCommnent: HashMap<String, Comment>? = hashMapOf(),
    val datetime: String? = ""
 )
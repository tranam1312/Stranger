package com.example.stranger.model

data class Comment(
    val key: String = "",
    val userId: String = "",
    var title: String = "",
    var listLikeComment: ArrayList<String> = arrayListOf(),
    var listReplyComment: HashMap<String, ReplyComment>? = hashMapOf(),
    var urlImg: String? = "",
    val datetime: String? = ""
)
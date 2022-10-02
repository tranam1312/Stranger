package com.example.stranger.model

data class Comment(
    val key: String? = null,
    val userId: String? =null,
    var title: String? = null,
    var listLikeComment: ArrayList<String>? = null,
    var listReplyComment: HashMap<String, ReplyComment>? = null,
    var urlImg: String? = null,
    val datetime: Long? =null
)
package com.example.stranger.model.response

data class ReplyComment(
    val uid: String? = null,
    val key: String? = null,
    var container: String? = null,
    var listLikeReplyComment: ArrayList<String>? = null,
    var urlImg: String? = null,
    val dateTime: String? = null
)
package com.example.stranger.model

data class  ReplyComment(
    val uid: String="",
    val key: String="",
    var container: String ="",
    var listLikeReplyComment: ArrayList<String>? = arrayListOf(),
    var urlImg: String?= "",
    val dateTime: String? ="")
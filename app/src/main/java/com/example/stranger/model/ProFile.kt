package com.example.stranger.model

import kotlin.collections.ArrayList
import kotlin.collections.HashMap


data class ProFile(
    val key: String? = null,
    var token: String? = null,
    val name: String? = null,
    val about: String? = null,
    val date: String? = null,
    val sex: String? =null,
    val from: String? =null,
    val liveAt: String? =null,
    val numberPhone: String? = null,
    val friendshipPurpose: String? = null,
    val maritalStatus: String? =null,
    val imgUrlAvatar: String? = null,
    val marriageStatus: String? = null,
    val keyPost: HashMap<String, String>? =null,
    val listImg: HashMap<String, String>? = null,
    val message: ArrayList<String>? = null,
    val groupMessage: ArrayList<String>? = null,
    val waitingMessage: ArrayList<String>? = null,
    val featuredPhoto: ArrayList<String>? = null
)

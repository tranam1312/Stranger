package com.example.stranger.model

import kotlin.collections.ArrayList
import kotlin.collections.HashMap


data class ProFile(
    val key:String?="",
    val name:String?="",
    val about: String? ="",
    val date: String= "",
    val sex:String ?="",
    val from:String ?="",
    val liveAt: String? ="",
    val numberPhone: String?="",
    val friendshipPurpose: String? = "",
    val maritalStatus: String? ="",
    val imgUrlAvatar: String? ="",
    val marriageStatus: String?="",
    val keyPost: HashMap<String, String>? = hashMapOf(),
    val listImg: HashMap<String, String>? = hashMapOf(),
    val message: ArrayList<String>? = arrayListOf(),
    val groupMessage: ArrayList<String>? = arrayListOf(),
    val waitingMessage: ArrayList<String>? = arrayListOf(),
    val featuredPhoto: ArrayList<String>? = arrayListOf())

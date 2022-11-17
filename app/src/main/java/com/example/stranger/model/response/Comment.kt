/*
 *  Created by Trần Nam on 11/5/22, 12:04 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 10/24/22, 2:18 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.model.response

data class Comment(
    val key: String? = null,
    val userId: String? =null,
    var title: String? = null,
    var listLikeComment: ArrayList<String>? = null,
    var listReplyComment: HashMap<String, ReplyComment>? = null,
    var urlImg: String? = null,
    val datetime: Long? =null
)
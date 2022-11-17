/*
 *  Created by Trần Nam on 11/11/22, 6:45 PM
 *    tranhoainam1312@gmail.com
 *     Last modified 11/11/22, 6:45 PM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.base

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingParent
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class CustomSwipeRefreshLayout : ViewGroup, NestedScrollingParent, NestedScrollingChild {
    private lateinit var onRefreshListener : SwipeRefreshLayout.OnRefreshListener
    private lateinit var process: CustomCircularProgressDrawable
    constructor(context: Context) : super(context){
        initView()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        initView()
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

    }
    fun initView(){
        process = CustomCircularProgressDrawable()

    }


}
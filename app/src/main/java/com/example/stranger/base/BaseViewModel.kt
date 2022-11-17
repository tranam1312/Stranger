/*
 *  Created by Trần Nam on 9/13/22, 12:58 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 9/13/22, 12:58 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val viewLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun showLoading() = viewLoading.postValue(true)

    fun hideLoading() = viewLoading.postValue(false)

}
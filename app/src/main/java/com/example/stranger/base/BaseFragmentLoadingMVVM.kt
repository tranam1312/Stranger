/*
 *  Created by Trần Nam on 11/16/22, 10:06 PM
 *    tranhoainam1312@gmail.com
 *     Last modified 11/16/22, 10:06 PM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragmentLoadingMVVM : BaseFragmentWithBinding() {
    protected var viewmodel: BaseViewModel? = getViewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewmodel = getViewModel()?.let { ViewModelProvider(this).get(it::class.java) }
        initViewLoadings()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getViewModel(): BaseViewModel?

    private fun initViewLoadings() {
        viewmodel?.let { viewmodel ->
            viewmodel.viewLoading.observe(viewLifecycleOwner) {
                if (it) showLoading() else hideLoading()
            }
        }
    }



}
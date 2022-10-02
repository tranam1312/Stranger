/*
 *  Created by Trần Nam on 8/27/22, 3:27 AM
 *    tranhoainam1312@gmail.com
 *     Last modified 8/27/22, 3:27 AM
 *     Copyright (c) 2022.
 *     All rights reserved.
 */

package com.example.stranger.ui.home.comment

import android.Manifest
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentCommentBinding
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CommentFragment : BaseFragmentWithBinding<FragmentCommentBinding>() {
    private val viewModel: CommentViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter
    private var key: String? = null

    companion object {
        fun newInstance(key: String?): CommentFragment {
            val fragment = CommentFragment()
            fragment.key = key
            return fragment
        }
    }

    override fun setToolBar(activity: AppCompatActivity) {
        binding.toolbar.apply {
            setTitle("Chi tiết")
            setIconLeft(R.drawable.ic_arrow_left)
            onBackPressed(activity)
        }
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentCommentBinding =
        FragmentCommentBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            commentAdapter = CommentAdapter()
            rvComment.adapter = commentAdapter
        }

    override fun init() {
        key?.let {
            viewModel.getItemHome(it)
            viewModel.getDataComment(it)
        }
    }

    override fun initAction() {
        binding.postComment.setOnClickListener {
            viewModel
        }

        binding.openLibrary.setOnClickListener {
            PermissionX.init(this)
                .permissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                )
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        openDialog()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "These permissions are denied: $deniedList",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    override fun initData() {
        viewModel.getItemHome.observe(viewLifecycleOwner) {
            binding.item = it
        }

        viewModel.proFile.observe(viewLifecycleOwner) {
            binding.profile = it
        }

        viewModel.comment.observe(viewLifecycleOwner) {
            commentAdapter.submitList(it)
        }
    }

}
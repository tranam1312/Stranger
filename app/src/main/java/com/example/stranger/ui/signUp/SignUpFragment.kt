package com.example.stranger.ui.signUp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragmentWithBinding<FragmentSignUpBinding>() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentSignUpBinding =
        FragmentSignUpBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
           viewmodel = viewModel
        }

    override fun init() {

    }

    override fun initData() {

    }

    override fun initAction() {
        binding.signUp.setOnClickListener { viewModel.login() }
    }

}
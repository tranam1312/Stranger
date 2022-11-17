package com.example.stranger.ui.setting.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentNewProFileBinding

class
ProFileFragment :BaseFragmentWithBinding<FragmentNewProFileBinding>() {

    companion object {
        fun newInstance() = ProFileFragment()
    }

    private lateinit var viewModel: ProFileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pro_file, container, false)
    }

    override fun init() {

    }

    override fun initData() {

    }

    override fun initAction() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProFileViewModel::class.java]

    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentNewProFileBinding = FragmentNewProFileBinding.inflate(inflater).apply {
        lifecycleOwner= viewLifecycleOwner
    }

}
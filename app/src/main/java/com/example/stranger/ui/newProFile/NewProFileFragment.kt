package com.example.stranger.ui.newProFile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.stranger.R
import com.example.stranger.base.BaseFragment
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.common.State
import com.example.stranger.common.succeeded
import com.example.stranger.databinding.FragmentNewProFileBinding

class NewProFileFragment : BaseFragmentWithBinding<FragmentNewProFileBinding>() {

    companion object {
        fun newInstance() = NewProFileFragment()
    }

    private lateinit var viewModel: NewProFileViewModel

    override fun getViewBinding(inflater: LayoutInflater): FragmentNewProFileBinding =
        FragmentNewProFileBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }

    override fun init() {

    }

    fun proFile() {
        viewModel.proFile.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> binding.buttonConfrim.isEnabled = false
                is State.Success -> findNavController().navigate(R.id.action_newProFileFragment_to_mainFragment)
                is State.Error -> {
                    binding.buttonConfrim.isEnabled = false
                    showSnackBar(it.exception)
                }

            }
        }
    }

    override fun initAction() {
        binding.buttonConfrim.setOnClickListener {

            if (binding.name.text.toString() != "") {
                viewModel.updateData(binding.name.text.toString())
            }

        }
    }

}
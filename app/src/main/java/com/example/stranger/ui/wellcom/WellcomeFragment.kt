package com.example.stranger.ui.wellcom


import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentWellcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WellcomeFragment : BaseFragmentWithBinding<FragmentWellcomeBinding>() {

    companion object {
        fun newInstance() = WellcomeFragment()
    }

    private val viewModel : WellcomeViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentWellcomeBinding =
       FragmentWellcomeBinding.inflate(inflater)
            .apply { lifecycleOwner = viewLifecycleOwner }

    override fun init() {
        binding.signIn.setOnClickListener { view ->

            view.findNavController().navigate(R.id.action_wellcomeFragment_to_signInFragment)
        }
        binding.signUp.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_wellcomeFragment_to_signUpFragment)
        }
    }

    override fun initAction() {
//        TODO("Not yet implemented")
    }

}
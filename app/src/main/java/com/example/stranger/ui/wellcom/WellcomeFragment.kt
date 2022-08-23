package com.example.stranger.ui.wellcom


import android.view.LayoutInflater
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentWellcomeBinding
import com.example.stranger.ui.main.MainFragment
import com.example.stranger.ui.signIn.SignInFragment
import com.example.stranger.ui.signUp.SignUpFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WellcomeFragment : BaseFragmentWithBinding<FragmentWellcomeBinding>() {

    companion object {
        fun newInstance() = WellcomeFragment()
    }


    override fun getViewBinding(inflater: LayoutInflater): FragmentWellcomeBinding =
        FragmentWellcomeBinding.inflate(inflater)
            .apply { lifecycleOwner = viewLifecycleOwner }

    override fun init() {

    }

    override fun initAction() {
        binding.signIn.setOnClickListener {
            replaceFragment(
                SignInFragment.newInstance(),
                SignInFragment::class.java.name
            )
        }

        binding.signUp.setOnClickListener {
            replaceFragment(
                SignUpFragment.newInstance(),
                SignInFragment::class.java.name
            )
        }
    }
}
package com.example.stranger.ui.signIn

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels

import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.stranger.R
import com.example.stranger.databinding.FragmentSignInBinding
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.common.State
import com.example.stranger.model.ProFile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragmentWithBinding<FragmentSignInBinding>() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private val viewModel: SignInViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater): FragmentSignInBinding =
        FragmentSignInBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.viewmodel = viewModel
        }

    override fun initAction() {
        binding.lognIn.setOnClickListener { view -> viewModel.login() }
        binding.signUp.setOnClickListener {
            findNavController().popBackStack(R.id.wellcomeFragment, true)
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.sigInGoogle.setOnClickListener { view ->
        }
    }

    override fun init() {
        firebaseUser()

    }

    private fun firebaseUser() {
        viewModel.firebaseUser.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> binding.lognIn.visibility = View.GONE
                is State.Success -> viewModel.getFroFile(it.data.uid)
                is State.Error -> {
                    binding.lognIn.visibility = View.VISIBLE
                    binding.textInputLayout.helperText = "Email ko tìm thấy"
                    binding.textInputLayout2.helperText = "Mật khẩu sai "
                    Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
                    binding.lognIn.isEnabled = true
                }
            }
        }
        getProfile()
    }

    private fun getProfile() {
        viewModel.proFile.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> binding.lognIn.visibility = View.GONE
                is State.Success -> checkDataProfile(it.data)

            }
        }
    }

    fun checkDataProfile(proFile: ProFile) {
        if (proFile.name.isNullOrEmpty())
            findNavController().navigate(R.id.action_signInFragment_to_newProFileFragment)
        else findNavController().navigate(R.id.action_signInFragment_to_mainFragment)
    }


}
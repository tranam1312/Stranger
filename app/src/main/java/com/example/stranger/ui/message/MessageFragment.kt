package com.example.stranger.ui.message

import android.app.PictureInPictureUiState
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.example.stranger.R
import com.example.stranger.base.BaseFragment
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.databinding.FragmentMessageBinding

class MessageFragment : BaseFragmentWithBinding<FragmentMessageBinding>() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    private lateinit var viewModel: MessageViewModel
    override fun getViewBinding(inflater: LayoutInflater): FragmentMessageBinding =
        FragmentMessageBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
            viewModel = ViewModelProvider(this@MessageFragment)[MessageViewModel::class.java]
        }

    override fun init() {

    }

    override fun initAction() {

    }


}
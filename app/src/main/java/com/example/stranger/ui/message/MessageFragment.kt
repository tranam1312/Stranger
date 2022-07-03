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

class MessageFragment : BaseFragment() {

    companion object {
        fun newInstance() = MessageFragment()
    }

    private val viewModel: MessageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return context?.let {
            ComposeView(it).apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    MessageScreen()
                }

            }
        }
    }

    @Composable
    fun MessageScreen(){
        BottomNavigation {
            BottomNavigationItem(false, onClick = {
            }, alwaysShowLabel = true, icon = {
})
        }
    }



}
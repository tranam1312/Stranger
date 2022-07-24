package com.example.stranger.ui.home.post

import androidx.lifecycle.ViewModel
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PostViewModel(val repository: Repository) : ViewModel() {

}
package com.example.stranger.ui.home.post

import androidx.lifecycle.ViewModel
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(val repository: Repository) : ViewModel() {

}
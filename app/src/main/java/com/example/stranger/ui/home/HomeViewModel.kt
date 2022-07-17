package com.example.stranger.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.common.succeeded
import com.example.stranger.model.ProFile
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _proFile: MutableLiveData<ProFile?> = MutableLiveData()
    val proFile get() = _proFile

    fun getFroFile() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.getProFile("").collect { proFile ->
                    when (proFile) {
                        is State.Success -> proFile.data?.let { _proFile.postValue(it) }
                        else -> {}
                    }
                }
            }
        }
    }
}
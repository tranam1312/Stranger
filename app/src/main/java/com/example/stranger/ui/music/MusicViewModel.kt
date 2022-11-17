package com.example.stranger.ui.music

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.model.response.Song
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _listSong: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    val listSong get() = _listSong

    fun getDataMusicServer() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getDataMusic().collect {
                    when (it) {
                        is State.Success -> {
                            _listSong.postValue(it.data.data?.song)
                        }
                        is State.Error -> Log.e("music", it.toString())
                    }
                }
            }
        }
    }
}
package com.example.stranger.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.extension.toArrayList
import com.example.stranger.model.ItemHome
import com.example.stranger.model.ProFile
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _proFile: MutableLiveData<ProFile?> = MutableLiveData()
    val proFile get() = _proFile
    private val _listItemHome: MutableLiveData<List<ItemHome>> = MutableLiveData()
    val listItemHome get() = _listItemHome

    init {
        getDataHome()
    }

    fun getDataHome() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.getDataHome().collect {
                    when (it) {
                        is State.Success -> {
                          _listItemHome.postValue(it.data)
                        }
                        else -> {

                        }
                    }
                }
            }


        }
    }

    fun getFroFile() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.getUid()?.let {
                    repository.getProFile(it).collect { proFile ->
                        when (proFile) {
                            is State.Success -> proFile.data?.let { _proFile.postValue(it) }
                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }
}
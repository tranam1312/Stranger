package com.example.stranger.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.model.response.ItemHome
import com.example.stranger.model.response.ProFile
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: Repository) : ViewModel() {
    private val _proFile: MutableLiveData<ProFile?> = MutableLiveData()
    val proFile get() = _proFile
    private val _listItemHome: MutableLiveData<List<ItemHome>> = MutableLiveData()
    val listItemHome get() = _listItemHome
    var updateItemHomeSize: MutableLiveData<Int> = MutableLiveData(0)
    var size: Int = 0


    init {
        getFroFile()
        getDataChangeHome()
    }

    fun getUid() = repository.getUid()

    fun getDataChangeHome() {
        viewModelScope.launch {
                repository.getDataChange().collect { respone ->
                    when (respone) {
                        is State.Success -> {
                            withContext(Dispatchers.IO) {
                            if (respone.data.size == size)
                                _listItemHome.postValue(respone.data)
                            else
                                updateItemHomeSize.postValue(respone.data.size - size)
                        }
                    }
                }
            }
        }
    }

    fun getDataHomeSever() {
        viewModelScope.launch {
            repository.getDataHome().collect { respone ->
                when (respone) {
                    is State.Success -> {
                        withContext(Dispatchers.IO) {
                            _listItemHome.postValue(respone.data)
                            size = respone.data.size
                            updateItemHomeSize.postValue(respone.data.size - size)
                        }
                    }
                }
            }
        }
    }

    fun updateItemHome(itemHome: ItemHome) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateItemHome(itemHome)
            }
        }
    }

    fun getFroFile() {
        viewModelScope.launch {

            repository.getUid()?.let {
                repository.getProFile(it).collect { proFile ->
                    when (proFile) {
                        is State.Success -> proFile.data?.let {
                            withContext(Dispatchers.Default) { _proFile.postValue(it) }
                        }
                    }
                }
            }
        }
    }


    fun updateLike(itemHome: ItemHome) {
        val list = itemHome?.listUserLike?.filter {
            it == getUid()
        } ?: arrayListOf()
        if (list.isNotEmpty()) itemHome?.listUserLike?.removeAll(list.toSet())
        else getUid()?.let { it1 ->
            itemHome?.listUserLike?.add(it1)
        }
        itemHome?.let { it1 -> updateItemHome(it1) }
    }
}
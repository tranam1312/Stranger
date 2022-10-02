package com.example.stranger.ui.home.comment

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.extension.Strings
import com.example.stranger.model.Comment
import com.example.stranger.model.ItemHome
import com.example.stranger.model.ProFile
import com.example.stranger.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _getItemHome: MutableLiveData<ItemHome> = MutableLiveData()
    val getItemHome: LiveData<ItemHome> get() = _getItemHome
    private val _proFile: MutableLiveData<ProFile> = MutableLiveData()
    val proFile: LiveData<ProFile> get() = _proFile
    private val _comment: MutableLiveData<ArrayList<Comment>> = MutableLiveData()
    val comment: LiveData<ArrayList<Comment>> get() = _comment
    var contentComment: String = Strings.EMPTY
    val showPostComment: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getItemHome(key: String) {
        viewModelScope.launch {

            repository.getItemHome(key).collect {
                when (it) {
                    is State.Loading -> {

                    }

                    is State.Success -> {
                        withContext(Dispatchers.Default) {
                            it.data.userid?.let { uid -> getProFile(uid) }
                        }
                    }
                }
            }
        }
    }

    private fun getProFile(uid: String) {
        viewModelScope.launch {
            repository.getProFile(uid).collect {
                when (it) {
                    is State.Loading -> {

                    }

                    is State.Success -> {
                        withContext(Dispatchers.IO) {
                            _proFile.postValue(it.data)
                        }
                    }
                }
            }
        }
    }

    fun getDataComment(key: String) {
        viewModelScope.launch {
            repository.getDataCommentChange(key).collect {
                when (it) {
                    is State.Loading -> {

                    }

                    is State.Success -> {
                        withContext(Dispatchers.IO) {
                            _comment.postValue(it.data)
                        }
                    }
                }
            }
        }
    }

    fun textChange(editable: Editable) {
        val s: String = editable.toString().trim()
        showPostComment.value = s.isNotEmpty()
    }


}
package com.example.stranger.ui.newProFile

import android.text.Editable
import androidx.annotation.NonNull
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.model.response.ProFile
import com.example.stranger.repository.Repository
import com.prdcv.ehust.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class NewProFileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var checkData: ObservableBoolean = ObservableBoolean()
    private val _proFile = SingleLiveEvent<State<ProFile>>()
    val proFile get() = _proFile

    fun updateData(@NonNull name: String?) {
        viewModelScope.launch {
            withContext(viewModelScope.coroutineContext) {
                repository.getUid()
                    ?.let {
                        repository.upDateProFile(it, ProFile(
                            token = repository.getUid(),
                            name = name
                        ))
                            .collect { proFile ->
                                _proFile.value = proFile
                            }
                    }
            }
        }
    }

    fun textChangedName(editable: Editable) {
        val s: String = editable.toString().trim()
        checkName(s)
    }

    fun checkName(s: String) {
        if (!s.isNullOrEmpty()) {
            if (Pattern.matches(
                    "[a-zA-Z_0-9]{0,1000}" + "[\\S+" +
                            "]" + "[a-zA-Z_0-9]{0,1000}", s
                )
            ) {
                checkData.set(true)
            } else checkData.set(false)
        } else checkData.set(false)
    }
}
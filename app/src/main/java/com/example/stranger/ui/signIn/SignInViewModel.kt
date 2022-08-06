package com.example.stranger.ui.signIn

import android.text.Editable
import androidx.annotation.NonNull
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.common.succeeded
import com.example.stranger.extension.Strings
import com.example.stranger.model.ProFile
import com.example.stranger.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.prdcv.ehust.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    var email: String = "admin12345@gmail.com"
    var pass: String = "admin123456"
    var checkEmail: Boolean = false
    var checkPass: Boolean = false
    val loginEnable: ObservableBoolean = ObservableBoolean()
    val hintEmail: MutableLiveData<String> = MutableLiveData()
    val hintPass: MutableLiveData<String> = MutableLiveData()
    val hintEmailTextColor: MutableLiveData<Boolean> = MutableLiveData(false)
    val hintPassTextColor: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _proFile = SingleLiveEvent<State<ProFile?>>()
    val proFile get() = _proFile

    private var _firebaseUser = SingleLiveEvent<State<FirebaseUser>>()
    val firebaseUser get() = _firebaseUser

    fun login() {
        if (enableLogin(checkEmail, checkPass, true))
            viewModelScope.launch() {
                withContext(Dispatchers.IO) {
                    repository.login(email, pass).collect {
                        _firebaseUser.postValue(it as State<FirebaseUser>?)
                    }
                }
            }
    }

    fun textChangedEmail(editable: Editable) {
        val s: String = editable.toString().trim()
        checkEmail(s)
    }

    fun checkEmail(s: String) {
        if (!s.isNullOrEmpty()) {
//            if (Pattern.matches(
////                    "[a-zA-Z_0-9]{0,1000}" + "@" + "gmail" + "\\." + "com",
////                    s
////                ) || Pattern.matches(
////                    "[a-zA-Z_0-9]{0,1000}" + "\\." + "[a-zA-Z_0-9]{0,1000}" + "@" + "gmail" + "\\." + "com",
////                    s
////                ) && !Pattern.matches("\t", s)
//            ) {
            checkEmail = true
            hintEmail.value = ""
            hintEmailTextColor.value = false
            enableLogin(checkEmail, checkPass)


        } else {
            checkEmail = false
            hintEmailTextColor.value = true
            hintEmail.value = "Email ko đúng định dạng"
        }
    }

    fun textChangedPass(editable: Editable) {
        val s: String = editable.toString().trim()
        checkPass(s)
    }


    fun checkPass(s: String) {
        if (!s.isNullOrEmpty()) {
            if (Pattern.matches(
                    "[a-zA-Z_0-9]{0,1000}" + "[\\S+" +
                            "]" + "[a-zA-Z_0-9]{0,1000}", s
                )
            ) {
                if (s.length > 8) {
                    checkPass = true
                    hintPassTextColor.value = false
                    hintPass.postValue("")
                    enableLogin(checkEmail, checkPass)
                } else {
                    checkPass = false
                    hintPassTextColor.value = true
                    hintPass.value = "Mật khẩu phải lớn hơn 8 "
                }
            } else {
                checkPass = false
                hintPassTextColor.value = true
                hintPass.value = "Mật khẩu phải lớn hơn 8 "
            }
        } else {
            hintPassTextColor.value = true
            hintPass.value = "Mời bạn nhập mật khẩu"
        }
    }

    fun enableLogin(checkEmail: Boolean, checkPass: Boolean, check: Boolean = false): Boolean {
        if (checkEmail && checkPass) {
            loginEnable.set(true)
            return true
        } else {
            loginEnable.set(false)
            if (check) {
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    checkEmail(email)
                    checkPass(pass)
                } else {
                    if (email.isEmpty()) {
                        hintEmailTextColor.value = true
                        hintEmail.value = "Mời bạn nhập email"
                    }
                    if (pass.isEmpty()) {
                        hintPassTextColor.value = true
                        hintPass.value = "Mời bạn nhập mật khẩu "
                    }
                }
            }
        }
        return false
    }


    fun getFroFile(uid: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.getProFile(uid).collect { proFile ->
                    _proFile.postValue(proFile)
                }
            }
        }
    }

    fun updateToken(proFile: ProFile) {
        viewModelScope.launch {
                repository.getUid()
                    ?.let {
                        repository.upDateProFile(it, proFile)
                            .collect { proFile ->
                                _proFile.value = proFile
                            }
                    }
                }
            }




}

package com.example.stranger.ui.signUp

import android.content.Context
import android.text.Editable
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stranger.common.State
import com.example.stranger.extension.Strings
import com.example.stranger.repository.Repository
import com.google.firebase.auth.FirebaseUser
import com.prdcv.ehust.common.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: Repository, private val context: Context) : ViewModel() {
    var email: String = Strings.EMPTY
    var pass: String = Strings.EMPTY
    var confirmPass : String = Strings.EMPTY
    var checkEmail: Boolean = false
    var checkPass: Boolean = false
    var checkConfirmPass: Boolean = false
    var check: Boolean = true
    val loginEnable: ObservableBoolean = ObservableBoolean()
    val hintEmail: MutableLiveData<String> = MutableLiveData()
    val hintPass: MutableLiveData<String> = MutableLiveData()
    val hintConfirmPass: MutableLiveData<String> = MutableLiveData()
    val hintEmailTextColor: MutableLiveData<Boolean> = MutableLiveData(false)
    val hintPassTextColor: MutableLiveData<Boolean> = MutableLiveData(false)
    val hintConfirmPassTextColor: MutableLiveData<Boolean> = MutableLiveData(false)


    private var _firebaseUser = SingleLiveEvent<State<FirebaseUser>>()
    val firebaseUser get() = _firebaseUser

    fun login() {
        if (enableLogin(checkEmail, checkPass, check , true))
            viewModelScope.launch() {
                withContext(Dispatchers.IO) {

                }
            }
    }

    fun textChangedEmail(editable: Editable) {
        val s: String = editable.toString().trim()
        checkEmail(s)
    }

    fun checkEmail(s: String) {
        if (!s.isNullOrEmpty()) {
            if (Pattern.matches(
                    "[a-zA-Z_0-9]{0,1000}" + "@" + "gmail" + "\\." + "com",
                    s
                ) || Pattern.matches(
                    "[a-zA-Z_0-9]{0,1000}" + "\\." + "[a-zA-Z_0-9]{0,1000}" + "@" + "gmail" + "\\." + "com",
                    s
                ) && !Pattern.matches("\t", s)
            ) {
                checkEmail = true
                hintEmail.value = ""
                hintEmailTextColor.value = false
                enableLogin(checkEmail, checkPass)
            } else {
                checkEmail = false
                hintEmailTextColor.value = true
                hintEmail.value = "Email ko đúng định dạng "
            }
        }
    }

    fun textChangedPass(editable: Editable) {
        val s: String = editable.toString().trim()
        checkPass(s)
    }

    fun textChangedConfirmPass(editable: Editable) {
        val s: String = editable.toString().trim()
        checkConfirmPass(s)
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

    fun checkConfirmPass(s: String) {
        if (!s.isNullOrEmpty()) {
            if (s == pass) {
                checkConfirmPass = true
                hintConfirmPassTextColor.value = true
                hintConfirmPass.value = ""
            } else {
                checkConfirmPass = false
                hintConfirmPassTextColor.value = false
                hintConfirmPass.value = "Mật khẩu xác nhận của  bạn nhập không khớp !"
            }
        }
    }

    fun enableLogin(checkEmail: Boolean, checkPass: Boolean,isCheck: Boolean = false, check: Boolean = false): Boolean {
        if (checkEmail && checkPass && checkConfirmPass && isCheck) {
            loginEnable.set(true)
            return true
        } else {
            loginEnable.set(false)
            if (check) {
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    checkEmail(email)
                    checkPass(pass)
                    checkConfirmPass
                } else {
                    if (email.isEmpty()) {
                        hintEmailTextColor.value = true
                        hintEmail.value = "Mời bạn nhập email !"
                    }
                    if (pass.isEmpty()) {
                        hintPassTextColor.value = true
                        hintPass.value = "Mời bạn nhập mật khẩu ! "
                    }
                    if (confirmPass.isEmpty()){
                        hintConfirmPass.value = "Mời bạn xác nhận mật khẩu của bạn !"
                    }
                }
            }
            if (!isCheck){
                Toast.makeText(context,"Bạn vui lòng đồng ý với các điều khoản", Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

}

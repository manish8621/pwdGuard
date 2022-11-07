package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.launch

class ResetPwdViewModel(application: Application) : AndroidViewModel(application) {
    val database = CredentialDb.getInstance(application)
    val repository= Repository(database)
    val authDetail = repository.authDetail

    var questionAnswerd = false
    var newPasswd= MutableLiveData("")
    var repeatPasswd= MutableLiveData("")

    var answer = MutableLiveData("")

    init {

    }
    fun answerMatches():Boolean{
        authDetail.value?.let {
            if(it.isNotEmpty() && (it[0].answer == (answer.value?:"")))
            {
                return true
            }
        }
        return false
    }
    fun updatePassword(){
        viewModelScope.launch {
            repository.changeAppPassword(newPasswd.value?:"")
        }
    }

}
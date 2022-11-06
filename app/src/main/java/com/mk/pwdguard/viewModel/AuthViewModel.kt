package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val database = CredentialDb.getInstance(application)
    val repository=Repository(database)
    val authDetail = repository.authDetail

    var newPasswd=MutableLiveData("")
    var repeatPasswd=MutableLiveData("")
    var passwd=MutableLiveData("")
//    var askPasswd = MutableLiveData(true)
    fun addNewPasswd(){
        newPasswd.value?.let{
            viewModelScope.launch { repository.putPasswd(DomainModels.Auth(it)) }
        }
    }

    fun passwdMatches() :Boolean{
        authDetail.value?.let {
            if(passwd.value!=null){
                if (it.isNotEmpty())
                    return (it.get(0).password == passwd.value)
            }
        }
        return false
    }
}
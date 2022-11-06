package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val database = CredentialDb.getInstance(application)
    val repository=Repository(database)
    val authDetail = repository.authDetail

    fun putPasswd(passwd:String){
        viewModelScope.launch{ repository.putPasswd(DomainModels.Auth(passwd)) }
    }

    fun passwdMatches(passwd:String) :Boolean{
        authDetail.value?.let {
            if(it.isNotEmpty())
                return (it.get(0).password == passwd)
        }
        return false
    }
}
package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.repository.AuthenticationRepository
import com.mk.pwdguard.model.repository.CredentialRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val database = CredentialDb.getInstance(application)

    private val authRepository= AuthenticationRepository(database)

    val authDetail = authRepository.getAuthDetail()

    var newPasswd=MutableLiveData("")
    var repeatPasswd=MutableLiveData("")
    var passwd=MutableLiveData("")
    var askPasswd = MutableLiveData(true)
    var question = ""
    var answer = MutableLiveData("")
    fun addNewPasswd(){
        newPasswd.value?.let{
            viewModelScope.launch { authRepository.saveAuthDetail(DomainModels.Auth(it,askPasswd.value?:true,question,answer.value?:"")) }
        }
    }

    fun passwdMatches() :Boolean{
        authDetail.value?.let {
            if(passwd.value!=null){
                if (it.isNotEmpty())
                    return (it[0].password == passwd.value)
            }
        }
        return false
    }
}
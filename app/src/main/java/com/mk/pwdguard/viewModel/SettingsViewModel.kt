package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.AuthenticationRepository
import com.mk.pwdguard.model.repository.CredentialRepository
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) :AndroidViewModel(application) {

    private val database = CredentialDb.getInstance(application)

    private val authRepository= AuthenticationRepository(database)

    val authDetail = authRepository.getAuthDetail()

    var oldPasswd= MutableLiveData("")
    var newPasswd= MutableLiveData("")

    fun passwordMatches():Boolean{
        authDetail.value?.let {
            if(it.isNotEmpty() && (it[0].password == (oldPasswd.value?:"")))
                return true
        }
        return false
    }

    fun updatePassword(){
        viewModelScope.launch {
            authRepository.changeAppPassword(newPasswd.value?:"")
        }
    }
}
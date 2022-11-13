package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.db.toDomainModels
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) :AndroidViewModel(application) {

    val database = CredentialDb.getInstance(application)
    val repository=Repository(database)
    val authDetail = repository.authDetail

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
            repository.changeAppPassword(newPasswd.value?:"")
        }
    }
}
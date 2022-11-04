package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowPwdViewModel(application: Application) :AndroidViewModel(application) {
    val database = CredentialDb.getInstance(application)
    val repository = Repository(database)
    val credentialList = repository.credentials
    fun delete(id:Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.delete(id)
            }
        }
    }

    }
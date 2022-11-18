package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.AuthenticationRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) :AndroidViewModel(application) {

    private val database = CredentialDb.getInstance(application)

    private val authRepository = AuthenticationRepository(database)

    val authDetail  = authRepository.getAuthDetail()

    fun isPasswordProtected():Boolean{
        return authDetail.value?.let {
            if(it.isNotEmpty()) it[0].authenticate else false
        }?:false
    }
    fun updateLockStatus(status:Boolean){
        viewModelScope.launch{ authRepository.updateAppLock(status) }
    }
}
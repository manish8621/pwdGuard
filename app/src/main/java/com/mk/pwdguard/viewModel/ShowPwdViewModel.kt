package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.CredentialRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowPwdViewModel(application: Application) :AndroidViewModel(application) {
    private val database = CredentialDb.getInstance(application)
    private val credentialRepository = CredentialRepository(database)
    var searchInProgress = false
    private val searchText = MutableLiveData("")
    val credentialList = Transformations.switchMap(searchText){ credentialRepository.getCredentialsList(it) }

    fun search(text:String) {
        searchText.value = text
        searchInProgress = true
    }

    fun lastSearchQuery() = searchText.value?:""
    fun onSearchCompleted(){
        searchInProgress = false
    }

    fun delete(id:Long){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                credentialRepository.deleteCredential(id)
            }
        }
    }

    }
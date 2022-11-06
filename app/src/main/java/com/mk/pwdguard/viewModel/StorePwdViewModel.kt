package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.databinding.Bindable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.domain.DomainModels.*
import com.mk.pwdguard.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class StorePwdViewModel(application: Application, id: Long) :AndroidViewModel(application) {
    private val database = CredentialDb.getInstance(application)

    private val repository = Repository(database)

    val credential = MutableLiveData<Credential>(Credential(title = "", site = "", username = "", password = "", lastUpdated = Date()))

    //for 2 way data binding
    var repeatPassword = MutableLiveData("")


    fun getCredentialToUpdate(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                credential.postValue(repository.getCredential(id))
            }
        }
    }

    fun addCredentialToDb(){
        credential.value?.let{
            //add current date
            it.lastUpdated = Date()
            viewModelScope.launch {
                //if id is >0 Credential is already and we need to update else save
                if(it.id>0)
                    repository.update(it)
                else
                    repository.insertIntoDb(it)
            }
        }
    }

    fun isNotEmptyCredentials():Boolean
    {
        var flag = false
        credential.value?.let {
            flag = it.title.validateText() && it.site.validateText() && it.username.validateText() && it.password.validateText()
        }
        return flag
    }
    private fun String.validateText():Boolean = isNotEmpty() && isNotBlank()
}
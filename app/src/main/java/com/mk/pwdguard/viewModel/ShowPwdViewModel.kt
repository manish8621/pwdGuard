package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.repository.Repository

class ShowPwdViewModel(application: Application) :AndroidViewModel(application) {
    val database = CredentialDb.getInstance(application)
    val repository = Repository(database)
    val credentialList = repository.credentials

    }
package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StorePwdViewModelFactory(private val application: Application,val id:Long):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StorePwdViewModel::class.java))
            return StorePwdViewModel(application,id) as T
        throw IllegalArgumentException("Illegal arg in factory(StorePwd)")
    }
}
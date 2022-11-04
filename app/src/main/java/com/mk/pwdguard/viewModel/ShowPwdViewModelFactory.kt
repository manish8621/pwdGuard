package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShowPwdViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ShowPwdViewModel::class.java))
            return ShowPwdViewModel(application) as T
        throw IllegalArgumentException("Illegal arg in factory(StorePwd)")
    }
}
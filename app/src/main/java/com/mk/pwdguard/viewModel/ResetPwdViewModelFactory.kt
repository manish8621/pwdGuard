package com.mk.pwdguard.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResetPwdViewModelFactory(private val application: Application):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ResetPwdViewModel::class.java))
            return ResetPwdViewModel(application) as T
        throw  IllegalArgumentException("illegal args at reset factory")
    }
}
package com.mk.pwdguard.viewModel

import android.app.Application
import android.view.ViewParent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(application) as T
        throw  IllegalArgumentException("illegal args at home factory")
    }
}
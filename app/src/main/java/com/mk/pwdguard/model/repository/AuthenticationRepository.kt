package com.mk.pwdguard.model.repository

import androidx.lifecycle.Transformations
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.db.toDomainModels
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.domain.asDatabaseModel
import com.mk.pwdguard.model.encrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(private val credentialDb:CredentialDb) {
    fun getAuthDetail() = Transformations.map(credentialDb.credentialDao.getAuthDetail()){
        it.toDomainModels()
    }

    suspend fun saveAuthDetail(auth: DomainModels.Auth){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.insertAuthDetail(auth.asDatabaseModel())
        }
    }

    suspend fun changeAppPassword(newPasswd:String){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.updateAuthPassword(encrypt(newPasswd))
        }
    }
    suspend fun updateAppLock(status:Boolean){
        withContext(Dispatchers.IO)
        {
            if (status) credentialDb.credentialDao.updateAuthStatus(1)
            else credentialDb.credentialDao.updateAuthStatus(0)
        }
    }
}
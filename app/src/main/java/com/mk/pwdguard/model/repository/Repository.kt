package com.mk.pwdguard.model.repository

import androidx.lifecycle.Transformations
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.db.DatabaseEntities
import com.mk.pwdguard.model.db.asDomainModel
import com.mk.pwdguard.model.db.asDomainModels
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.domain.asDataBaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(private val credentialDb: CredentialDb) {
    val credentials = Transformations.map(credentialDb.credentialDao.getCredentials()){
        it.asDomainModels()
    }

    suspend fun insertIntoDb(credential:DomainModels.Credential){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.insert(credential.asDataBaseModel())
        }
    }

    suspend fun getAllCredentials()= withContext(Dispatchers.IO){
        credentialDb.credentialDao.getCredentials()
    }

    suspend fun getCredential(id:Long)= withContext(Dispatchers.IO){
        credentialDb.credentialDao.getCredential(id).asDomainModel()
    }

    suspend fun update(credential:DomainModels.Credential){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.update(credential.asDataBaseModel())
        }
    }
    suspend fun delete(id:Long){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.delete(id)
        }
    }
}
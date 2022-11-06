package com.mk.pwdguard.model.repository

import androidx.lifecycle.Transformations
import com.mk.pwdguard.model.db.*
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.domain.asDataBaseModel
import com.mk.pwdguard.model.domain.asDatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//split into repos
//put into application class
//make this as singleton
class Repository(private val credentialDb: CredentialDb) {
    val credentials = Transformations.map(credentialDb.credentialDao.getCredentials()){
        it.asDomainModels()
    }
    val authDetail = Transformations.map(credentialDb.credentialDao.getPasswd()){
        it.toDomainModels()
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

    suspend fun putPasswd(auth: DomainModels.Auth){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.putPasswd(auth.asDatabaseModel())
        }

    }
}
package com.mk.pwdguard.model.repository

import androidx.lifecycle.Transformations
import com.mk.pwdguard.model.db.CredentialDb
import com.mk.pwdguard.model.db.asDomainModel
import com.mk.pwdguard.model.db.asDomainModels
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.model.domain.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//split into repos
//put into application class
//make this as singleton
class CredentialRepository(private val credentialDb: CredentialDb) {
//    val credentials = Transformations.map(credentialDb.credentialDao.getCredentials("")){
//        it.asDomainModels()
//    }
//    val authDetail = Transformations.map(credentialDb.credentialDao.getPasswd()){
//        it.toDomainModels()
//    }









    suspend fun insertIntoDb(credential:DomainModels.Credential){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.insertCredential(credential.asDataBaseModel())
        }
    }


    //added
    fun getCredentialsList(searchText: String)= Transformations.map(credentialDb.credentialDao.getCredentials(searchText )){
            it.asDomainModels()
        }

    suspend fun getCredential(id:Long)= withContext(Dispatchers.IO){
        credentialDb.credentialDao.getCredential(id).asDomainModel()
    }

    suspend fun updateCredential(credential:DomainModels.Credential){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.updateCredential(credential.asDataBaseModel())
        }
    }

    suspend fun deleteCredential(id:Long){
        withContext(Dispatchers.IO){
            credentialDb.credentialDao.deleteCredential(id)
        }
    }


}
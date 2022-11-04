package com.mk.pwdguard.model.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CredentialDao{

    @Insert
    fun insert(credential: DatabaseEntities.Credential)

    @Query("SELECT * FROM credential_table")
    fun getCredentials():LiveData<List<DatabaseEntities.Credential>>

    @Query("SELECT * FROM credential_table where id = :id")
    fun getCredential(id:Long) : DatabaseEntities.Credential

    @Query("Delete FROM credential_table where id=:id")
    fun delete(id:Long)

    @Update
    fun update(credential: DatabaseEntities.Credential)

}
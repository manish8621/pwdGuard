package com.mk.pwdguard.model.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CredentialDao{

    @Insert
    fun insertCredential(credential: DatabaseEntities.Credential)

    @Query("SELECT * FROM credential_table WHERE title like :searchText || '%' OR site like :searchText || '%' OR username like :searchText || '%'")
    fun getCredentials(searchText:String):LiveData<List<DatabaseEntities.Credential>>

    @Query("SELECT * FROM credential_table where id = :id")
    fun getCredential(id:Long) : DatabaseEntities.Credential


    @Query("Delete FROM credential_table where id=:id")
    fun deleteCredential(id:Long)

    @Update
    fun updateCredential(credential: DatabaseEntities.Credential)

    //Auth related
    @Query("SELECT * FROM auth_table")
    fun getAuthDetail() : LiveData<List<DatabaseEntities.Auth>>

    @Insert
    fun insertAuthDetail(auth:DatabaseEntities.Auth)

    @Query("UPDATE auth_table set password=:passwd")
    fun updateAuthPassword(passwd:String)

    @Query("UPDATE auth_table set authenticate=:status")
    fun updateAuthStatus(status:Int)
}
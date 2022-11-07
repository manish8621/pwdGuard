package com.mk.pwdguard.model.db

import android.content.Context
import android.util.Log
import androidx.room.*

@Database(entities = [DatabaseEntities.Credential::class,DatabaseEntities.Auth::class], version = 2, exportSchema = false)
abstract class CredentialDb:RoomDatabase() {
    abstract val credentialDao:CredentialDao
    companion object{
        private var INSTANCE:CredentialDb? = null
        fun getInstance(context: Context):CredentialDb
        {

            if(INSTANCE == null) {
                val instance =
                    Room.databaseBuilder(context, CredentialDb::class.java, "credential_database")
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
            }
                return INSTANCE as CredentialDb
        }
    }

}
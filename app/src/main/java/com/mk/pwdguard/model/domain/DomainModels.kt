package com.mk.pwdguard.model.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mk.pwdguard.model.db.DatabaseEntities
import com.mk.pwdguard.model.encrypt
import java.util.*

class DomainModels {


        data class Credential(
            var id : Long=0L,
            var title: String,
            var site: String,
            var username: String,
            var password: String,
            var lastUpdated: Date,
        )
    data class Auth(
        val password: String,
        val authenticate:Boolean,
        val securityQuestion:String,
        val answer:String
    )
}

fun DomainModels.Credential.asDataBaseModel():DatabaseEntities.Credential{
    return DatabaseEntities.Credential(
        id = id,
        title = title,
        site = site,
        username = username,
        password = encrypt(password),
        lastUpdated = lastUpdated.time
    )
}
fun DomainModels.Auth.asDatabaseModel():DatabaseEntities.Auth{
    return DatabaseEntities.Auth(
        encrypt(password),
        authenticate,
        securityQuestion,
        answer
    )
}
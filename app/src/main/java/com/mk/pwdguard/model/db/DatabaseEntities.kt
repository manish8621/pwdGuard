package com.mk.pwdguard.model.db

import android.icu.text.CaseMap.Title
import androidx.annotation.ColorInt
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mk.pwdguard.model.domain.DomainModels
import java.util.Date

class DatabaseEntities{
    @Entity(tableName = "credential_table")
    data class Credential(
        @PrimaryKey(autoGenerate = true)
        var id: Long=0L,
        var title: String,
        var site: String,
        var username: String,
        val password: String,
        @ColumnInfo(name = "last_updated") var lastUpdated: Long,
    )
}
fun List<DatabaseEntities.Credential>.asDomainModels():List<DomainModels.Credential>{
    return map{
        it.asDomainModel()
    }
}
fun DatabaseEntities.Credential.asDomainModel():DomainModels.Credential{
    return DomainModels.Credential(
        id = id,
        title = title,
        site = site,
        username = username,
        password = password,
        lastUpdated = Date(lastUpdated)
    )
}
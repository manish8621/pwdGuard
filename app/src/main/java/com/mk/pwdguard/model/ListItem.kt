package com.mk.pwdguard.model

import com.mk.pwdguard.model.domain.DomainModels

sealed class ListItem {
    data class CredentialItem(val credential: DomainModels.Credential): ListItem() {
        override val id = credential.id
    }
    object HeaderItem:ListItem(){
        override val id: Long = -1
    }
    abstract val id:Long
}
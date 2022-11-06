package com.mk.pwdguard.model

//util functions

fun String.smartShrink()=if(length<15) this else substring(0,15)+"..."
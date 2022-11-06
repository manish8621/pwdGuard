package com.mk.pwdguard.model


    private const val masterKey = "qw08t123"
    private const val MAX_CHAR_CODE = 127
    /*
    * this function decrypts the given string with given key returns result string
    * if key is not given it uses master key given default in this class
    * **/
    fun decrypt(encrypted:String,key:String = masterKey):String{
        val decrypted = StringBuilder()
        var keyIndex = 0
        for (c in encrypted) {
            //xor before calculate the new char ascii value
            var code = (c.code xor keyIndex) - masterKey[keyIndex].code

            //if char value goes lesser than 0 subtract the code value from max value
            if((code) <= 0)
                code = (MAX_CHAR_CODE + code)

            //add
            decrypted.append((code).toChar())

            //change key index
            keyIndex = if(keyIndex==masterKey.length-1) 0 else keyIndex+1
        }
        return decrypted.toString()
    }
    /*
    * this function encrypts the given string with given key returns encrypted string
    * if key is not given it uses master key given default in this class
    * **/
    fun encrypt(text:String):String{
        val encrypted = StringBuilder()
        var keyIndex = 0
        for (c in text) {
            //calculate the new char ascii value
            var code = c.code + masterKey[keyIndex].code

            //if char value goes greater than specified start from 0
            if((code)>MAX_CHAR_CODE)
                code = (code - MAX_CHAR_CODE)

            //xor before storing
            code = code xor keyIndex

            //add the char to string builder
            encrypted.append(code.toChar())

            //change keyIndex
            keyIndex = if(keyIndex==masterKey.length-1) 0 else keyIndex+1
        }
        return encrypted.toString()
    }

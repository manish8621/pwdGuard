package com.mk.pwdguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mk.pwdguard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}
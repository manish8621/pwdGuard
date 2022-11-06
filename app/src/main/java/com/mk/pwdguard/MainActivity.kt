package com.mk.pwdguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.mk.pwdguard.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    //biometric
//    private lateinit var executer:Executor
//    private lateinit var biometricPrompt: BiometricPrompt
//    private lateinit var promptInfo:PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.fragmentContainerView.visibility = View.INVISIBLE
//        Toast.makeText(this@MainActivity, "Locked", Toast.LENGTH_SHORT).show()

//        executer = ContextCompat.getMainExecutor(this)
//        biometricPrompt = BiometricPrompt(this,executer,
//            object:BiometricPrompt.AuthenticationCallback(){
//                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
//                    super.onAuthenticationError(errorCode, errString)
////                    Toast.makeText(this@MainActivity, "error $errString", Toast.LENGTH_SHORT).show()
//                    if(errString=="use different method")
//                    {
//                        Toast.makeText(this@MainActivity, "currently no alternate", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                    super.onAuthenticationSucceeded(result)
//                    Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
//                    binding.fragmentContainerView.visibility = View.VISIBLE
//
//                }
//
//                override fun onAuthenticationFailed() {
//                    super.onAuthenticationFailed()
//                    Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
//                }
//            })
//        promptInfo = BiometricPrompt.PromptInfo.Builder()
//            .setTitle("Authentication")
//            .setSubtitle("Biometric login")
//            .setNegativeButtonText("use different method")
//            .build()
//        biometricPrompt.authenticate(promptInfo)
    }

}
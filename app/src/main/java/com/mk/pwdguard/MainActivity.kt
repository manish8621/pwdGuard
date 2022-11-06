package com.mk.pwdguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.mk.pwdguard.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    //biometric
    private lateinit var executer:Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo:PromptInfo
    private lateinit var bioMetrricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        executer = ContextCompat.getMainExecutor(this)
        bioMetrricManager = BiometricManager.from(this)

    }
    fun biometricAuth(
        onResult:(success:Boolean)->Unit
    ){

        biometricPrompt = BiometricPrompt(this,executer,
            object:BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onResult.invoke(false)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onResult.invoke(true)

                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onResult.invoke(false)
                    Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_SHORT).show()
                }
            })
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Authentication")
            .setSubtitle("Biometric login")
            .setNegativeButtonText("has")
            .build()
        if ( bioMetrricManager.canAuthenticate(BIOMETRIC_STRONG or  DEVICE_CREDENTIAL)==BiometricManager.BIOMETRIC_SUCCESS)
            biometricPrompt.authenticate(promptInfo)
    }
}
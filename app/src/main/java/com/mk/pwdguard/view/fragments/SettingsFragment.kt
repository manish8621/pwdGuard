package com.mk.pwdguard.view.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentSettingsBinding
import com.mk.pwdguard.viewModel.SettingsViewModel
import com.mk.pwdguard.viewModel.SettingsViewModelFactory


class SettingsFragment : Fragment() {
    private lateinit var binding :FragmentSettingsBinding
    private lateinit var  viewModel : SettingsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val factory = SettingsViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[SettingsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //stop loading after db result loaded
        viewModel.authDetail.observe(viewLifecycleOwner){
            stopLoading()
        }

        (activity as MainActivity).changeActionBarTitle("Settings")


        setOnClickListeners()
        return binding.root
    }

    private fun stopLoading() {
        with(binding){
            progressBar.isVisible = false
            changePasswordTv.isVisible = true
            oldPasswordTv.isVisible = true
            oldPasswordEt.isVisible = true
            newPasswordTv.isVisible = true
            newPasswordEt.isVisible = true
            repeatPasswordTv.isVisible = true
            repeatPasswordEt.isVisible = true
            submitBtn.isVisible = true
            eyeOldPwdBtn.isVisible = true
            eyeNewPwdBtn.isVisible = true
            eyeRepeatPwdBtn.isVisible = true
        }
    }

    private fun setOnClickListeners() {
        //eye buttons
        binding.eyeNewPwdBtn.setOnClickListener{
            eyeButtonHandler(it as ImageView,binding.newPasswordEt)
        }
        binding.eyeRepeatPwdBtn.setOnClickListener{
            eyeButtonHandler(it as ImageView,binding.repeatPasswordEt)
        }
        binding.eyeOldPwdBtn.setOnClickListener{
            eyeButtonHandler(it as ImageView,binding.oldPasswordEt)
        }

        //check password and update
        binding.submitBtn.setOnClickListener{

            if(areFieldsNotEmpty())
            {
                if(binding.newPasswordEt.text.toString() == binding.repeatPasswordEt.text.toString())
                {
                    if (viewModel.passwordMatches()) {
                        viewModel.updatePassword()
                        toast("Password changed successfully")
                        findNavController().navigateUp()
                    }
                    else {
                        toast("Wrong password")
                        toast(viewModel.authDetail.value?.toString()?:"null")
                    }
                }
                else toast("Password doesn't match")
            }
            else toast("Empty fields")

        }
    }

    private fun toast(text:String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    private fun areFieldsNotEmpty(): Boolean {
        return binding.newPasswordEt.text.toString().isNotEmpty()
                && binding.oldPasswordEt.text.toString().isNotEmpty()
                && binding.repeatPasswordEt.text.toString().isNotEmpty()
    }

    private fun eyeButtonHandler(imageView: ImageView, passwordEt: EditText) {
        if((imageView).tag =="hidden"){
            imageView.setImageResource(R.drawable.eye_cross)
            passwordEt.transformationMethod = HideReturnsTransformationMethod.getInstance()
            passwordEt.moveCursorToVisibleOffset()
            (imageView).tag ="shown"
        }
        else{
            (imageView).tag ="hidden"
            imageView.setImageResource(R.drawable.eye)
            passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()
        }
    }
}
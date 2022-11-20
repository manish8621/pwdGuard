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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentResetPwdBinding
import com.mk.pwdguard.viewModel.ResetPwdViewModel


class ResetPwdFragment : Fragment() {


    private lateinit var binding :FragmentResetPwdBinding
    private lateinit var viewModel : ResetPwdViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPwdBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[ResetPwdViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        (activity as MainActivity).changeActionBarTitle("Reset password")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.authDetail.observe(viewLifecycleOwner){
            if(it!=null && it.isNotEmpty()) {
                val question = "Question : ${it[0].securityQuestion}"
                binding.questionTv.text = question
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        //eye buttons
        binding.eyeNewPwdBtn.setOnClickListener{
            eyeButtonHandler(it as ImageView,binding.newPasswordEt)
        }
        binding.eyeRepeatPwdBtn.setOnClickListener{
            eyeButtonHandler(it as ImageView,binding.repeatPasswordEt)
        }

        //this click listener do two functions
        binding.submitBtn.setOnClickListener{

            if(viewModel.questionAnswerd) handleResettingPassword()
            else handleSecurityQuestion()

        }
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

    private fun handleSecurityQuestion() {
        //show question
        //validate answer
        if(binding.answerEt.text.isNotEmpty() && binding.answerEt.text.toString().isNotBlank() )
        {
            if(viewModel.answerMatches())
            {
                //hide question answer
                hideQuestionLayout()
                //show passWord input
                showPasswordLayout()
                //make method for this
                viewModel.questionAnswerd = true
            }
            else Toast.makeText(context, "wrong answer", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "empty fields", Toast.LENGTH_SHORT).show()
    }

    private fun handleResettingPassword() {
        val newPwd = binding.newPasswordEt.text.toString()
        val repeatPwd = binding.repeatPasswordEt.text.toString()

        if(newPwd.isNotEmpty() && newPwd.isNotBlank()
            && repeatPwd.isNotEmpty() && repeatPwd.isNotBlank() )
        {
            if(newPwd.length<4 && repeatPwd.length<4){
                Toast.makeText(context, "minimum password length is 4", Toast.LENGTH_SHORT).show()
                return
            }
            //if all are valid
            if(newPwd == repeatPwd)
            {
                Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show()
                //update password
                viewModel.updatePassword()
                findNavController().navigate(R.id.action_resetPwdFragment_to_homeFragment)
            }

            else Toast.makeText(context, "password not matching", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "empty fields !", Toast.LENGTH_SHORT).show()
    }

    private fun showPasswordLayout() {
        binding.resetPasswordGroup.visibility = View.VISIBLE
    }

    private fun hideQuestionLayout() {
        binding.questionLayoutGroup.visibility = View.INVISIBLE
    }


}
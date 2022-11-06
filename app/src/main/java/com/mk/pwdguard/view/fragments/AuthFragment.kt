package com.mk.pwdguard.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentAuthBinding
import com.mk.pwdguard.viewModel.AuthViewModel


class AuthFragment : Fragment() {
    //try two way data binding
    private lateinit var binding :FragmentAuthBinding
    private lateinit var viewModel :AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.authDetail.observe(viewLifecycleOwner){
            hideProgressBar()
            if(it.isNotEmpty()){
                showAuthLayout()
            }
            else
            {
                showCreatePasswordLayout()
            }
        }
        setOnClickListeners()
    }

    private fun showAuthLayout() {
        binding.authPasswordLayout.visibility = View.VISIBLE
    }

    private fun showCreatePasswordLayout() {
        binding.createPasswordLayout.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility=View.GONE
    }


    private fun setOnClickListeners() {
        with(binding)
        {
            submitBtn.setOnClickListener {
                val newPasswd = newPasswordEt.text.toString()
                val repeatPaswd = repeatPasswordEt.text.toString()
                if(newPasswd.isEmpty() || repeatPaswd.isEmpty())
                {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else
                {
                    if(newPasswd == repeatPaswd) {
                        viewModel.putPasswd(newPasswd)
                        Toast.makeText(context, "Password added", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context, "Passwords not matching", Toast.LENGTH_SHORT).show()
                }
            }
            unlockBtn.setOnClickListener{
                val passwd = passwordEt.text.toString()

                if(passwd.isEmpty())
                {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(viewModel.passwdMatches(passwd)) {
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                        Toast.makeText(context, "Unlock success", Toast.LENGTH_SHORT).show()
                }
                else
                        Toast.makeText(context, "Incorrect Password !", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
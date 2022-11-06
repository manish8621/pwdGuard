package com.mk.pwdguard.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentAuthBinding
import com.mk.pwdguard.viewModel.AuthViewModel


class AuthFragment : Fragment() {
    //try two way data binding
    private lateinit var binding :FragmentAuthBinding
    private lateinit var viewModel :AuthViewModel
    private val animationDuration = 100L
    private val animationDistance = 20.0F
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
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
        //to create password
        binding.submitBtn.setOnClickListener {
                val newPasswd = binding.newPasswordEt.text.toString()
                val repeatPasswd = binding.repeatPasswordEt.text.toString()
                if(newPasswd.isEmpty() || repeatPasswd.isEmpty())
                {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else
                {
                    if(newPasswd == repeatPasswd) {
                        viewModel.putPasswd()
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                        Toast.makeText(context, "Password added", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(context, "Passwords not matching", Toast.LENGTH_SHORT).show()
                }
            }
        //for unlock
        binding.unlockBtn.setOnClickListener{
                val passwd = binding.passwordEt.text.toString()

                if(passwd.isEmpty())
                {
                    Toast.makeText(context, "Empty fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(viewModel.passwdMatches()) {
                    (it as Button).text = getString(R.string.unlocked)
                    it.also { view ->view.setBackgroundColor(requireContext().getColor(R.color.green))  }.animate().translationY(animationDistance).setDuration(animationDuration).withEndAction {
                        it.animate().translationY(0.0F).setDuration(animationDuration).withEndAction {
                            it.animate().translationY(animationDistance).setDuration(animationDuration).withEndAction {
                                it.animate().translationY(0.0F).setDuration(animationDuration).withEndAction {
                                    findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                                }.start()
                            }.start()
                        }.start()
                    }.start()

                        Toast.makeText(context, "Unlock success", Toast.LENGTH_SHORT).show()
                }
                else {

                    Toast.makeText(context, "Incorrect Password !", Toast.LENGTH_SHORT).show()
                    (it as Button).text = getString(R.string.try_again)
                    animateError(it)
                }
            }
    }

    private fun animateSuccess(it:View) {
        it.also { view ->view.setBackgroundColor(requireContext().getColor(R.color.green))  }.animate().translationY(animationDistance).setDuration(animationDuration).withEndAction {
            it.animate().translationY(0.0F).setDuration(animationDuration).withEndAction {
                it.animate().translationY(animationDistance).setDuration(animationDuration).withEndAction {
                    it.animate().translationY(0.0F).setDuration(animationDuration).start()
                }.start()
            }.start()
        }.start()
    }

    private fun animateError(it: View) {

        it.also { view ->view.setBackgroundColor(requireContext().getColor(R.color.red))  }.animate().translationX(animationDistance).setDuration(animationDuration).withEndAction {
            it.animate().translationX(0.0F).setDuration(animationDuration).withEndAction {
                it.animate().translationX(animationDistance).setDuration(animationDuration).withEndAction {
                    it.also { view ->view.setBackgroundColor(requireContext().getColor(R.color.light_blue))}.animate().translationX(0.0F).setDuration(animationDuration).start()
                }.start()
            }.start()
        }.start()
    }

}
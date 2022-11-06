package com.mk.pwdguard.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentShowPwdBinding
import com.mk.pwdguard.databinding.FragmentStorePwdBinding
import com.mk.pwdguard.model.domain.DomainModels
import com.mk.pwdguard.viewModel.StorePwdViewModel
import com.mk.pwdguard.viewModel.StorePwdViewModelFactory
import java.util.*

class StorePwdFragment : Fragment() {


    // do Two way databainding ####################
    lateinit var binding: FragmentStorePwdBinding
    lateinit var viewModel : StorePwdViewModel
    val args : StorePwdFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStorePwdBinding.inflate(inflater,container,false)
        val factory = StorePwdViewModelFactory(requireActivity().application,args.id)
        viewModel = ViewModelProvider(this,factory)[StorePwdViewModel::class.java]
        binding.viewModel = viewModel
        //set title
        //TODO: replace the (args.id>0) with a function
        (activity as MainActivity).title = if(args.id>0) "Edit Credential" else "Add Credential"

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.id>0)
            viewModel.getCredentialToUpdate(args.id)
        //set observers if update mode

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.saveBtn.setOnClickListener{
            getInputs()
            if(viewModel.isNotEmptyCredentials()){
                if(binding.passwordEt.text.toString() == binding.repeatPasswordEt.text.toString()) {
                    viewModel.addCredentialToDb()
                    Toast.makeText(context, if(args.id>0) "Credential updated" else "Credential added", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(activity, "Passwords not matching", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(activity, "Blank fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getInputs(){
        viewModel.credential.value?.also {
            it.title = binding.titleEt.text.toString()
            it.site = binding.siteEt.text.toString()
            it.username = binding.usernameEt.text.toString()
            it.password = binding.passwordEt.text.toString()
        }
    }
    private fun idExists(id:Long){

    }

}
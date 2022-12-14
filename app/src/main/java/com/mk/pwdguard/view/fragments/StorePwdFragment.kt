package com.mk.pwdguard.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.databinding.FragmentStorePwdBinding
import com.mk.pwdguard.viewModel.StorePwdViewModel
import com.mk.pwdguard.viewModel.StorePwdViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StorePwdFragment : Fragment() {

    lateinit var binding: FragmentStorePwdBinding
    lateinit var viewModel : StorePwdViewModel
    private val args : StorePwdFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStorePwdBinding.inflate(inflater,container,false)

        //view Model
        val factory = StorePwdViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[StorePwdViewModel::class.java]
        binding.viewModel = viewModel

        //set title
        (activity as MainActivity).changeActionBarTitle(if(idExists()) "Edit Credential" else "Add Credential")

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(idExists()) viewModel.getCredentialToUpdate(args.id)

        setOnClickListeners()
    }


    private fun setOnClickListeners() {
        binding.saveBtn.setOnClickListener{
            if(viewModel.isNotEmptyCredentials()){
                if(binding.pwdEt.text.toString() == binding.repeatPwdEt.text.toString()) {
                    //navigate up after saving
                    viewModel.addCredentialToDb{
                        CoroutineScope(Dispatchers.Main).launch {
                            findNavController().navigateUp()
                        }
                    }
                    Toast.makeText(context, if(idExists()) "Credential updated" else "Credential added", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(activity, "Passwords not matching", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(activity, "Blank fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun idExists():Boolean{
        return args.id > 0
    }

}
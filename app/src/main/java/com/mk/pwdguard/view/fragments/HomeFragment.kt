package com.mk.pwdguard.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentHomeBinding
import com.mk.pwdguard.viewModel.HomeViewModel
import com.mk.pwdguard.viewModel.HomeViewModelFactory

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var viewModel:HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val factory = HomeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
//        viewModel.credentialList.observe(viewLifecycleOwner){
//            Toast.makeText(activity, it?.toString()?:"null", Toast.LENGTH_SHORT).show()
//            Log.i("TAG",it?.toString()?:"null")
//        }
    }

    private fun setOnClickListeners() {

        with(binding){
            addBtn.setOnClickListener{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStorePwdFragment(-1))
            }
            showBtn.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_showPwdFragment)
            }
        }
    }

}
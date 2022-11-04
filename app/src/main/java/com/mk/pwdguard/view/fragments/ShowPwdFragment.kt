package com.mk.pwdguard.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentHomeBinding
import com.mk.pwdguard.databinding.FragmentShowPwdBinding
import com.mk.pwdguard.databinding.FragmentStorePwdBinding
import com.mk.pwdguard.view.adapters.CredentialAdapter
import com.mk.pwdguard.viewModel.ShowPwdViewModel
import com.mk.pwdguard.viewModel.ShowPwdViewModelFactory
import com.mk.pwdguard.viewModel.StorePwdViewModel
import com.mk.pwdguard.viewModel.StorePwdViewModelFactory

class ShowPwdFragment : Fragment() {

    lateinit var viewModel:ShowPwdViewModel
    lateinit var binding: FragmentShowPwdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowPwdBinding.inflate(inflater,container,false)
        val factory = ShowPwdViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[ShowPwdViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CredentialAdapter()
        adapter.setOnclickListener {
            findNavController().navigate(ShowPwdFragmentDirections.actionShowPwdFragmentToStorePwdFragment(it.id))
        }
        adapter.setDeleteBtnClickListener {
            viewModel.delete(it.id)
        }
        adapter.setCopyBtnClickListener {
            Toast.makeText(context, "Copied !", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
        viewModel.credentialList.observe(viewLifecycleOwner){
            it?.let {
                adapter.submitList(it)
            }
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {

    }


}
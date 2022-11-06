package com.mk.pwdguard.view.fragments

import android.content.ClipData
import android.content.ClipData.Item
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.databinding.FragmentShowPwdBinding
import com.mk.pwdguard.view.adapters.CredentialAdapter
import com.mk.pwdguard.viewModel.ShowPwdViewModel
import com.mk.pwdguard.viewModel.ShowPwdViewModelFactory

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

        //
        showStatus("Loading ...")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CredentialAdapter()
        adapter.setOnclickListener {
            findNavController().navigate(ShowPwdFragmentDirections.actionShowPwdFragmentToStorePwdFragment(it.id))
        }
        adapter.setDeleteBtnClickListener {
            viewModel.delete(it)
        }
        adapter.setCopyBtnClickListener { text->
            val clipBoardManager = (activity as MainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipBoardManager.setPrimaryClip(ClipData.newPlainText("login detail",text))
            Toast.makeText(context, "Copied $text", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerView.adapter = adapter
        viewModel.credentialList.observe(viewLifecycleOwner){
            it?.let {
                if(it.isEmpty())
                    showStatus("No Credentials")
                else
                    hideStatus()
                adapter.submitList(it)
            }
        }
        setOnClickListeners()
    }

    private fun hideStatus() {
        binding.statusTv.visibility = View.INVISIBLE
    }

    private fun showStatus(status: String) {
        binding.statusTv.text = status
        binding.statusTv.visibility = View.VISIBLE
    }

    private fun setOnClickListeners() {

    }


}
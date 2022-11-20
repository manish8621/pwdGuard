package com.mk.pwdguard.view.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentShowPwdBinding
import com.mk.pwdguard.view.adapters.CredentialAdapter
import com.mk.pwdguard.viewModel.ShowPwdViewModel
import com.mk.pwdguard.viewModel.ShowPwdViewModelFactory

class ShowPwdFragment : Fragment() {

    lateinit var viewModel:ShowPwdViewModel
    lateinit var binding: FragmentShowPwdBinding
    private lateinit var adapter: CredentialAdapter
    lateinit var alertDialogBuilder: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowPwdBinding.inflate(inflater,container,false)
        val factory = ShowPwdViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[ShowPwdViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner

        setupAlertDialog()

        (activity as MainActivity).changeActionBarTitle("Pwd guard")
        showStatus("Loading ...")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //for searching
        setupSearchView()

        //recycler view
        adapter = CredentialAdapter()
        setOnClickListeners()
        binding.recyclerView.adapter = adapter

        //observing for data
        setupObservers()
    }

    private fun setupAlertDialog() {
        alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            .setCancelable(true)
            .setIcon(AppCompatResources.getDrawable(requireContext(), R.drawable.delete_dark))
            .setTitle("Delete")
            .setMessage("Are you sure ?")
            .setNegativeButton("") { dialog, _ -> dialog.cancel() }
            .setNegativeButtonIcon(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.close_small
                )
            )
            .setPositiveButtonIcon(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.done_small
                )
            )
    }


    private fun setupObservers() {
        viewModel.credentialList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
                if (it.isEmpty()) {
                    if (viewModel.searchInProgress) {
                        //if searched is not found show not found
                        // if search query is empty and result is empty then show no data in db
                        showStatus(
                            if (viewModel.lastSearchQuery()
                                    .isNotEmpty()
                            ) "No results found" else "No Credentials"
                        )
                        viewModel.onSearchCompleted()
                    } else {
                        showStatus("No Credentials")
                    }
                } else hideStatus()
            }
        }
    }

    private fun setupSearchView() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                query?.toString()?.let { viewModel.search(it) }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.searchIb.setOnClickListener {
            viewModel.search(binding.searchEt.text.toString())
        }
    }

    private fun hideStatus() {
        binding.statusTv.visibility = View.INVISIBLE
    }

    private fun showStatus(status: String) {
        binding.statusTv.text = status
        binding.statusTv.visibility = View.VISIBLE
    }

    private fun setOnClickListeners() {

        adapter.setOnClickListeners(object :CredentialAdapter.ClickListener{
            override fun onDeleteBtnClicked(id: Long) {
                alertDialogBuilder.setPositiveButton(""){ dialog,_ -> viewModel.delete(id); dialog.cancel()}
                alertDialogBuilder.show()
            }
            override fun onCopyBtnClicked(text: String) {
                val clipBoardManager = (activity as MainActivity).getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipBoardManager.setPrimaryClip(ClipData.newPlainText("login detail",text))
                Toast.makeText(context, "Copied $text", Toast.LENGTH_SHORT).show()
            }
            override fun onSiteViewClicked(url: String) {
                openBrowser(url)
            }

            override fun onEditClicked(id: Long) {
                findNavController().navigate(ShowPwdFragmentDirections.actionShowPwdFragmentToStorePwdFragment(id))
            }
        })
    }

    private fun openBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        Toast.makeText(context, "opening $url", Toast.LENGTH_SHORT).show()
        i.data = Uri.parse(if (!url.startsWith("https://")) "https://$url" else url)
        requireActivity().startActivity(i)
    }


}
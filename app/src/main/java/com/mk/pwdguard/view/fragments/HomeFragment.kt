package com.mk.pwdguard.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Switch
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mk.pwdguard.MainActivity
import com.mk.pwdguard.R
import com.mk.pwdguard.databinding.FragmentHomeBinding
import com.mk.pwdguard.viewModel.HomeViewModel
import com.mk.pwdguard.viewModel.HomeViewModelFactory

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var viewModel:HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val factory = HomeViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner

        //title
        (activity as MainActivity).changeActionBarTitle("Home")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setOverflowMenu()
        setObserver()
    }



    private fun setObserver() {
        viewModel.authDetail.observe(viewLifecycleOwner){
            if(binding.lockStatusIv.isVisible.not()) binding.lockStatusIv.visibility = View.VISIBLE
            if(viewModel.isPasswordProtected())
                showLockedUi()
            else
                showUnlockedUi()
        }
    }

    private fun showUnlockedUi() {
        binding.appLockSw.isChecked = false
        binding.lockStatusIv.setImageResource(R.drawable.shield_broken)
    }

    private fun showLockedUi() {
        binding.appLockSw.isChecked = true
        binding.lockStatusIv.setImageResource(R.drawable.shild_protected)
    }

    private fun setOverflowMenu() {
        (context as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.settings_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId == R.id.settings_menu_item) {
                    goToSettingsPage()
                }
                return true
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)
    }

    private fun setOnClickListeners() {

        with(binding){
            addBtn.setOnClickListener{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToStorePwdFragment(-1))
            }
            showBtn.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_showPwdFragment)
            }
            appLockSw.setOnClickListener {
                //TODO:Binding adapter
                if((it as SwitchMaterial).isChecked){
                    viewModel.updateLockStatus(true)
                    binding.lockStatusIv.setImageResource(R.drawable.shild_protected)
                }
                else{
                    viewModel.updateLockStatus(false)
                    binding.lockStatusIv.setImageResource(R.drawable.shield_broken)
                }
            }
        }
    }
    fun goToSettingsPage(){
        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
    }
}
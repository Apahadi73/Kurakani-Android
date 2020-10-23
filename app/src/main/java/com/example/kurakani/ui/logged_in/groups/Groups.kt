package com.example.kurakani.ui.logged_in.groups

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kurakani.R
import com.example.kurakani.databinding.FragmentGroupsBinding
import com.example.kurakani.ui.logged_in.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class Groups : Fragment() {

    private lateinit var groupViewModel: GroupsViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var binding:FragmentGroupsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_groups, container, false)
        groupViewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)
        groupViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textGroups.text = it
        })
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button even
                    Log.d("BACKBUTTON", "Back button clicks")
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return binding.root
    }
}
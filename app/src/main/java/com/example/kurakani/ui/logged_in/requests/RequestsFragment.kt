package com.example.kurakani.ui.logged_in.requests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kurakani.R
import com.example.kurakani.adapters.FriendsListAdapter
import com.example.kurakani.databinding.FragmentRequestsBinding

class RequestsFragment : Fragment() {

    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requests, container, false)
        requestsViewModel = ViewModelProvider(this).get(RequestsViewModel::class.java)
        requestsViewModel.fetchRequestList()
        if (requestsViewModel.requestList.value != null) {
            requestsViewModel.requestList.observe(viewLifecycleOwner, Observer {
                binding.requestRecyclerView.adapter =
                    FriendsListAdapter(requestsViewModel.requestList)
            })
        }

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
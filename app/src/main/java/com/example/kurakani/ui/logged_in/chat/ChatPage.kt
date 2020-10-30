package com.example.kurakani.ui.logged_in.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.kurakani.R
import com.example.kurakani.databinding.ChatPageFragmentBinding
import com.example.kurakani.databinding.RequestItemCardBinding

class ChatPage : Fragment() {

    private lateinit var chatPageViewModel: ChatPageViewModel
    private lateinit var binding: ChatPageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_page_fragment, container, false)
//      receive chatId from argument Bundle
        val chatId = arguments?.let { ChatPageArgs.fromBundle(it).chatId }
        return binding.root
    }
}
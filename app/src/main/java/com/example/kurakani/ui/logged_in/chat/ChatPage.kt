package com.example.kurakani.ui.logged_in.chat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kurakani.R
import com.example.kurakani.adapters.recyclerviewAdapters.ChatPageListAdapter
import com.example.kurakani.adapters.recyclerviewAdapters.MessageListAdapter
import com.example.kurakani.adapters.recyclerviewAdapters.UserChatClickListner
import com.example.kurakani.databinding.ChatPageFragmentBinding
import com.example.kurakani.databinding.RequestItemCardBinding
import com.example.kurakani.ui.logged_in.home.HomeFragmentDirections
import com.example.kurakani.ui.logged_in.home.HomeViewModel

class ChatPage : Fragment() {

    private lateinit var chatPageViewModel: ChatPageViewModel
    private lateinit var binding: ChatPageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_page_fragment, container, false)
        chatPageViewModel = ViewModelProvider(this).get(ChatPageViewModel::class.java)
//      receive chatId from argument Bundle
        val chatId = arguments?.let { ChatPageArgs.fromBundle(it).chatId }
        if (chatId != null) {
            chatPageViewModel.setChatId(chatId)
            chatPageViewModel.fetchMessages()
        }
        val adapter = ChatPageListAdapter()
        binding.recyclerView.adapter = adapter
        chatPageViewModel.messages.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }
}
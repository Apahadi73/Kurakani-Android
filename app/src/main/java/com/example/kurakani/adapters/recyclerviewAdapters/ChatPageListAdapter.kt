package com.example.kurakani.adapters.recyclerviewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kurakani.databinding.MessageReceivedItemBinding
import com.example.kurakani.model.Message
import com.squareup.picasso.Picasso


class ChatPageListAdapter() :
    ListAdapter<Message, ChatPageListAdapter.MessageViewHolder>(ChatMessagesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatPageListAdapter.MessageViewHolder {
        return ChatPageListAdapter.MessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatPageListAdapter.MessageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class MessageViewHolder private constructor(val binding: MessageReceivedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentItem: Message?
        ) { //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) {
                Picasso.get().load(currentItem.profile_pic).into(binding.userCardImage)
                binding.message =
                    Message(currentItem.message, currentItem.sender_name, currentItem.profile_pic,currentItem.time)
            }
        }

        companion object {
            fun from(
                parent: ViewGroup
            ): ChatPageListAdapter.MessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MessageReceivedItemBinding.inflate(layoutInflater, parent, false)
                return ChatPageListAdapter.MessageViewHolder(binding)
            }
        }
    }

}

class ChatMessagesDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem == newItem
    }

}


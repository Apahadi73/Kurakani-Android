package com.example.kurakani.adapters.recyclerviewAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.example.kurakani.databinding.UserItemCardBinding
import com.example.kurakani.model.UserMessageInfo


class MessageListAdapter(private val messageInfoList: MutableLiveData<List<UserMessageInfo>>) :
    ListAdapter<UserMessageInfo, MessageListAdapter.MessageViewHolder>(MessageListDiffCallback()) {
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder.from(parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = getItem(position)
        Log.d("reached ", "reached onBindViewHolder")
        holder.bind(currentItem, holder)
    }


    //    view holder class
    class MessageViewHolder(val binding: UserItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            currentItem: UserMessageInfo?,
            holder: MessageViewHolder
        ) { //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) Picasso.get().load(currentItem.imageSrc)
                .into(holder.binding.userCardImage)
            if (currentItem != null) holder.binding.username.text = currentItem.userName
            if (currentItem != null) holder.binding.message.text = currentItem.message
        }

        companion object {
            fun from(parent: ViewGroup): MessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = UserItemCardBinding.inflate(layoutInflater, parent, false)
                return MessageViewHolder(binding)
            }
        }
    }
}

class MessageListDiffCallback : DiffUtil.ItemCallback<UserMessageInfo>() {
    override fun areItemsTheSame(oldItem: UserMessageInfo, newItem: UserMessageInfo): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(
        oldItem: UserMessageInfo,
        newItem: UserMessageInfo
    ): Boolean {
        return oldItem == newItem
    }

}

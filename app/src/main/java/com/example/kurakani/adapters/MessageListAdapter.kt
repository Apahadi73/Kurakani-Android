package com.example.kurakani.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_item_card.view.*

import com.example.kurakani.R
import com.example.kurakani.model.UserMessage


class MessageListAdapter(private val MessageList: MutableLiveData<List<UserMessage>>) : RecyclerView.Adapter<MessageListAdapter.MessageViewHolder>() {
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item_card,
            parent, false
        )
        return MessageViewHolder(itemView)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = MessageList.value?.get(position)
        Log.d("reached ","reached onBindViewHolder")
//        downloads and populate imageview with user image from firebase image storage
        if (currentItem != null) {
            Picasso.get().load(currentItem.imageSrc).into(holder.imageView)
        }
        if (currentItem != null) {
            holder.userName!!.text = currentItem.userName
        }
        if (currentItem != null) {
            holder.message!!.text = currentItem.message
        }
    }

    override fun getItemCount(): Int = MessageList.value?.size ?:0

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.user_card_image
        val userName: AppCompatTextView? = itemView.username
        val message: AppCompatTextView? = itemView.message
    }
}

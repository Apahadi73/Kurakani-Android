package com.example.kurakani.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.kurakani.R
import com.example.kurakani.model.Request
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.request_item_card.view.*
import kotlinx.android.synthetic.main.user_item_card.view.user_card_image
import kotlinx.android.synthetic.main.user_item_card.view.username


class RequestListListAdapter(private val RequestList: MutableLiveData<List<Request>>) :
    RecyclerView.Adapter<RequestListListAdapter.RequestViewHolder>() {
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(this,parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val currentItem = RequestList.value?.get(position)
        holder.bind(currentItem, holder)
    }


    override fun getItemCount(): Int = RequestList.value?.size ?: 0

    class RequestViewHolder private  constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.user_card_image
        val userName: AppCompatTextView? = itemView.username
        val acceptBtn: AppCompatButton? = itemView.request_accept_btn

        fun bind(
            currentItem: Request?,
            holder: RequestViewHolder
        ) {
            //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) {
                Picasso.get().load(currentItem.imageSrc).into(holder.imageView)
            }
            if (currentItem != null) {
                holder.userName!!.text = currentItem.userName
            }
        }

        companion object {
            fun from(requestListListAdapter: RequestListListAdapter, parent: ViewGroup): RequestViewHolder {
                val itemView = LayoutInflater.from(parent.context).inflate(
                    R.layout.request_item_card,
                    parent, false
                )
                return RequestViewHolder(itemView)
            }
        }

        override fun onClick(v: View?) {

        }

    }




}

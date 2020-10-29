package com.example.kurakani.adapters.recyclerviewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kurakani.databinding.RequestItemCardBinding
import com.example.kurakani.model.Request
import com.squareup.picasso.Picasso


class RequestListListAdapter(val clickListener:AcceptButtonListner,private val RequestList: MutableLiveData<List<Request>>) :
    ListAdapter<Request, RequestListListAdapter.RequestViewHolder>(RequestListDiffCallback()) {
    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(this, parent)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position)!!,clickListener)
    }

    class RequestViewHolder private constructor(val binding: RequestItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val acceptBtn: AppCompatButton? = binding.requestAcceptBtn

        fun bind(
            currentItem: Request?,
            holder: AcceptButtonListner
        ) { //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) {
                Picasso.get().load(currentItem.imageSrc).into(binding.userCardImage)
                binding.request = Request(currentItem.imageSrc,currentItem.userName,currentItem.userId)
            }
        }

        companion object {
            fun from(
                requestListListAdapter: RequestListListAdapter,
                parent: ViewGroup
            ): RequestViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RequestItemCardBinding.inflate(layoutInflater, parent, false)
//                val itemView = LayoutInflater.from(parent.context).inflate(
//                    R.layout.request_item_card,
//                    parent, false
//                )
                return RequestViewHolder(binding)
            }
        }
    }

}

class RequestListDiffCallback : DiffUtil.ItemCallback<Request>() {
    override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(
        oldItem: Request,
        newItem: Request
    ): Boolean {
        return oldItem == newItem
    }

}

//click listner
class AcceptButtonListner(val clickListener: (userName: String) -> Unit) {
    fun onClick(request: Request) = clickListener(request.userName)
}

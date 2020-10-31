//package com.example.kurakani.adapters.recyclerviewAdapters
//
//package com.example.kurakani.adapters.recyclerviewAdapters
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.appcompat.widget.AppCompatButton
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.example.kurakani.databinding.RequestItemCardBinding
//import com.example.kurakani.model.Request
//import com.squareup.picasso.Picasso
//
//
//class RequestListListAdapter(private val clickListener: AcceptButtonListner) :
//    ListAdapter<Request, RequestListListAdapter.RequestViewHolder>(RequestListDiffCallback()) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
//        return RequestViewHolder.from(parent)
//    }
//
//    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
//        holder.bind(getItem(position)!!,clickListener)
//    }
//
//    class RequestViewHolder private constructor(val binding: RequestItemCardBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(
//            currentItem: Request?,
//            clickListener: AcceptButtonListner
//        ) { //        downloads and populate imageview with user image from firebase image storage
//            if (currentItem != null) {
//                Picasso.get().load(currentItem.imageSrc).into(binding.userCardImage)
//                binding.request = Request(currentItem.imageSrc,currentItem.userName,currentItem.userId)
//                binding.acceptBtnListner = clickListener
//            }
//        }
//
//        companion object {
//            fun from(
//                parent: ViewGroup
//            ): RequestViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = RequestItemCardBinding.inflate(layoutInflater, parent, false)
//                return RequestViewHolder(binding)
//            }
//        }
//    }
//
//}
//
//class RequestListDiffCallback : DiffUtil.ItemCallback<Request>() {
//    override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
//        return oldItem.userName == newItem.userName
//    }
//
//    override fun areContentsTheSame(
//        oldItem: Request,
//        newItem: Request
//    ): Boolean {
//        return oldItem == newItem
//    }
//
//}
//
////click listner
//class AcceptButtonListner(val clickListener: (request: Request) -> Unit) {
//    fun onClick(request: Request) = clickListener(request)
//}

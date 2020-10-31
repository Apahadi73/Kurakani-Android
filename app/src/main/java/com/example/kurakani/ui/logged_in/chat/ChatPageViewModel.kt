package com.example.kurakani.ui.logged_in.chat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kurakani.model.Message
import com.example.kurakani.model.UserMessageInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatPageViewModel : ViewModel() {
    private lateinit var user: FirebaseUser
    private lateinit var database: DatabaseReference


    private val _messageList = MutableLiveData<List<Message>>().apply {
//        load empty array list to instantiate the message list live data
        value = ArrayList<Message>()
    }

    val messages = _messageList

    private val _chatId = MutableLiveData<String>().apply {
        value = ""
    }

    fun setChatId(id: String): Unit {
        _chatId.value = id
    }

    //  fetches messages from chat database
    fun fetchMessages() {
        user = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadData(dataSnapshot)
            }

            //            loads messageInfo into the messagelist live data
            private fun loadData(dataSnapshot: DataSnapshot) {
                val messages = ArrayList<Message>()
                val responseData = dataSnapshot.child("chats").child(_chatId.value.toString())
                    .child("messages")
                for (messageData in responseData.children){
                    val message =messageData.child("message").value.toString()
                    val profilePic =messageData.child("profile_pic").value.toString()
                    val senderName =messageData.child("sender_name").value.toString()
                    val time =messageData.child("time").value.toString()
                    messages+=Message(message,senderName,profilePic,time)
                }
                Log.d("message",messages.toString())
                _messageList.value = messages
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }
}
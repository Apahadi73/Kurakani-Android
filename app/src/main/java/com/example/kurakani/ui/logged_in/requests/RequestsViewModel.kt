package com.example.kurakani.ui.logged_in.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kurakani.model.Chat
import com.example.kurakani.model.Friend
import com.example.kurakani.model.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RequestsViewModel : ViewModel() {

    private lateinit var database: DatabaseReference
    private lateinit var user: FirebaseUser

    private var _requestList = MutableLiveData<List<Request>>().apply {
        value = ArrayList<Request>()

    }
    val requestList = _requestList

    fun acceptRequest(request: Request): Unit {
//        todo: this uuid has to come from the user that sends the request.
        val chatId = UUID.randomUUID().toString()
        val friend = Friend(request.imageSrc, request.userId, request.userName, chatId)
        val participants = listOf<String>(friend.uid,user.uid)
        val chat = Chat("Congratulation. You guys are connected now.",participants)

//        save accepted users in friends hashmap
        database.child("users").child(user.uid).child("friends").child(friend.uid).setValue(friend)
//        removes accepted user from request list
        database.child("users").child(user.uid).child("requests").child(friend.uid).removeValue()
//        creates a new chat collection for connected users
        database.child("chats").child(chatId).setValue(chat)
    }

    //    fetches request list from realtime database
    fun fetchRequestList(): Unit {
        user = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadData(dataSnapshot)
            }

            // loads data into the requestList live data
            fun loadData(dataSnapshot: DataSnapshot) {
                val responseData =
                    dataSnapshot.child("users").child(user.uid).child("requests")

                val requestList = ArrayList<Request>()
//                load request into the requestList arraylist
                for (user in responseData.children) {
                    val request = user.value as HashMap<*, *>
                    requestList += Request(
                        request["profile_pic"] as String,
                        request["name"] as String,
                        request["userId"] as String,
                    )
                }
                // loads the data into the request list livedata
                _requestList.value = requestList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }
}
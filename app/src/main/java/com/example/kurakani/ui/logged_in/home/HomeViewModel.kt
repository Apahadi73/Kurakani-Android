package com.example.kurakani.ui.logged_in.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kurakani.model.UserMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class HomeViewModel : ViewModel() {
    private lateinit var imageSrc: String
    private lateinit var message: String
    private lateinit var userName: String
    private lateinit var user: FirebaseUser
    private lateinit var database: DatabaseReference
    private var isDataLoaded = false


    private val _messageList = MutableLiveData<List<UserMessage>>().apply {
        Log.d("response", "reached")
        value = generateDummyList(20)
    }
    val messageList = _messageList

    //    for the moment - generates dummy data
    // TODO : fetch data from backend
    private fun generateDummyList(size: Int): List<UserMessage> {
        val messageList = ArrayList<UserMessage>()
        for (i in 0 until size) {
            messageList += if (isDataLoaded) {
                val item = UserMessage(imageSrc, userName, message)
                item
            } else {
                val item = UserMessage(
                    "https://storage.needpix.com/rsynced_images/android-icon-2332747_1280.png",
                    "User: ${i + 1}",
                    "message: hi user ${i + 1}"
                )
                item
            }
         }
        return messageList
    }

    //  fetches user name from cloud firestore and updates the profile view
    fun fetchUserData() {
        Log.d("response", "fetched")
        user = FirebaseAuth.getInstance().currentUser!!
        userName = user.displayName.toString()
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val responseData = dataSnapshot.child("users").child(user.uid)
                imageSrc = responseData.child("profile_image").value.toString()
//                todo: make this dynamic
                message = "Hi there"
                isDataLoaded = true
                _messageList.value = generateDummyList(20)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addListenerForSingleValueEvent(valueListner)
    }
}
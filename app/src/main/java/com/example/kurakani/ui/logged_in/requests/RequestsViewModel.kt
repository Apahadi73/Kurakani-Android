package com.example.kurakani.ui.logged_in.requests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kurakani.model.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlin.random.Random

class RequestsViewModel : ViewModel() {

    private lateinit var database: DatabaseReference
    private lateinit var user: FirebaseUser
    private lateinit var userNameList: ArrayList<String>

    private var _requestList = MutableLiveData<List<Request>>().apply {
        value = generateDummyList(20)
        Log.d("requestList", value.toString())

    }
    val requestList = _requestList

//    load this data while data is being fetched
    private fun generateDummyList(size: Int): ArrayList<Request> {
        val dummyUserNameList = arrayOf("Amir", "Samir", "Sanskar", "Ravi", "Asim", "Aavash")
        Log.d("request_section", "generating request list")
        val requestList = ArrayList<Request>()
        for (i in 0 until size) {
            requestList += Request(
                "https://storage.needpix.com/rsynced_images/android-icon-2332747_1280.png",
                dummyUserNameList[Random.nextInt(5)],
                userId = dummyUserNameList[Random.nextInt(5)],
            )
        }
        return requestList
    }


    //    fetches request list from realtime database
    fun fetchRequestList(): Unit {
        user = FirebaseAuth.getInstance().currentUser!!
        Log.d("request_section", user.toString())
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val responseData = dataSnapshot.child("users").child(user.uid).child("user_info").child("requests")
                var userList = responseData.value as ArrayList<*>
//                clears null values from userList
                userList = userList.filterNotNull() as ArrayList<Any>

                val requestList = ArrayList<Request>()
                for (request in userList) {
                    val user = request as HashMap<*, *>
                    requestList+=Request(user["profile_pic"] as String,user["name"] as String,user["userId"] as String,)
                }
                _requestList.value=requestList
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addListenerForSingleValueEvent(valueListner)
    }
}
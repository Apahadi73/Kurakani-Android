package com.example.kurakani.ui.logged_in.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ProfileViewModel : ViewModel() {
    private lateinit var imageSrc: String
    private lateinit var user: FirebaseUser
    private lateinit var database: DatabaseReference

    private val _profileImageSrc = MutableLiveData<String>().apply {
        value = "https://upload.wikimedia.org/wikipedia/commons/8/82/Android_logo_2019.svg"
    }
    val profileImage: LiveData<String> = _profileImageSrc
    private val _profileName = MutableLiveData<String>().apply {
        value = "User Name"
    }
    val profileName: LiveData<String> = _profileName

    //  fetches user name from cloud firestore and updates the profile view
    fun fetchUserData() {
        Log.d("response", "fetched")
        user = FirebaseAuth.getInstance().currentUser!!
        _profileName.value = user.displayName.toString()
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val responseData = dataSnapshot.child("users").child(user.uid)
                imageSrc = responseData.child("profile_image").value.toString()
                Log.d("response",imageSrc)
                _profileImageSrc.value = imageSrc
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addListenerForSingleValueEvent(valueListner)
    }
}
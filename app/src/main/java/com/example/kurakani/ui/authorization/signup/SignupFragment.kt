package com.example.kurakani.ui.authorization.signup

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.kurakani.R
import com.example.kurakani.databinding.FragmentSignupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


//sign up fragment
class SignupFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignupBinding
    private var SELECT_PHOTO: Int = 1
    private lateinit var mStorageRef: StorageReference;
    private lateinit var myDBref: DatabaseReference
    private lateinit var imageURI: Uri
    private var imageDownloadUrl = ""

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.loginButton.setOnClickListener(Navigation.createNavigateOnClickListener((R.id.action_signupFragment2_to_loginFragment2)))
        binding.signupButton.setOnClickListener {
            if (container != null) {
                signUpUser(container.context.applicationContext)
            }
        }
        //        picks an image from user
        binding.pickImage.setOnClickListener {
            var intent: Intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, SELECT_PHOTO)
        }
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button even
                    Log.d("BACKBUTTON", "Back button clicks")
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return binding.root
    }

    //    handles image picker result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_PHOTO) {
            // handle chosen image
            if (data != null) {
                imageURI = data.data!!
                binding.profileAvatar.setImageURI(imageURI)
            }
        }
    }

    //   uploads avatar image and saves downloadurl into imageUrl
    private fun uploadAvatarImage(data: Uri, userId: String) {
        //        realtime database reference
        val database = FirebaseDatabase.getInstance()
        myDBref = database.reference
        mStorageRef = FirebaseStorage.getInstance().reference;
        val riversRef: StorageReference = mStorageRef.child("images/${userId}")
        riversRef.putFile(imageURI)
            .addOnSuccessListener { taskSnapshot -> // Get a URL to the uploaded content
                // Get a URL to the uploaded content
                val downloadTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                downloadTask.addOnSuccessListener {
                    imageDownloadUrl = it.toString()
                    Log.d("response", "downloadUrl:$imageDownloadUrl")
                    myDBref.child("users").child(userId).child("profile_pic")
                        .setValue(imageDownloadUrl)
                    myDBref.child("users").child(userId).child("user_info").child("user_name")
                        .setValue(auth.currentUser?.displayName)
//                        after saving data to the realtime database, navigates to homepage
                    findNavController().navigate(R.id.action_signupFragment2_to_userActivity)
                }
                Log.d("response", "Successfully uploaded the image to firebase")
            }
            .addOnFailureListener {
                Log.d("response", "Unsuccessful to upload the image to firebase")
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun signUpUser(context: Context): Unit {
        if (binding.userName.toString().isEmpty()) {
            binding.userName.error = "Please enter a valid user name"
            binding.userName.requestFocus()
            return
        }
        if (binding.userEmail.toString().isEmpty()) {
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.userEmail.text.toString()).matches()) {
            binding.userEmail.error = "Please enter a valid email address"
            binding.userEmail.requestFocus()
            return
        }
        if (binding.userPassword.toString().isEmpty() || binding.userPassword.toString().length < 6) {
            binding.userPassword.error = "Please enter a valid password"
            binding.userPassword.requestFocus()
            return
        }


        activity?.mainExecutor?.let {
            auth.createUserWithEmailAndPassword(binding.userEmail.text.toString(), binding.userPassword.text.toString())
                .addOnCompleteListener(it, OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail: success")
                        // THE USER ID
                        val userId = task.result?.user?.uid;
                        val user = FirebaseAuth.getInstance().currentUser

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(binding.userName.text.toString())
                            .build()

                        user!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "User profile updated.")
                                }
                            }
//                        upload image
                        if (userId != null) {
                            uploadAvatarImage(imageURI, userId)
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail: failure", task.exception)
                        Toast.makeText(
                            context, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
        }

    }
}


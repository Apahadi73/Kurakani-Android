package com.example.kurakani

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AuthorizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
    }
}

//class AuthenticationActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding:ActivityAuthorizationBinding = DataBindingUtil.setContentView(this, R.layout.activity_authorization)
//    }
//}
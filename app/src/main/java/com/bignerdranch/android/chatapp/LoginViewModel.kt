package com.bignerdranch.android.chatapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth

    init {
        auth.addAuthStateListener {
            user.value = it.currentUser
        }
    }
    val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData<String>()

    fun signIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                error.value = it.message
            }
    }

}
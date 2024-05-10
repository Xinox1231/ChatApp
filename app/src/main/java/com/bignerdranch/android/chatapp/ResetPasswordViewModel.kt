package com.bignerdranch.android.chatapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ResetPasswordViewModel: ViewModel() {

    private val auth = Firebase.auth
    val error = MutableLiveData<String>()
    val isSuccess = MutableLiveData<Boolean>()

    fun resetPassword(email: String){
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                isSuccess.value = true
            }
            .addOnFailureListener {
                error.value = it.message
            }
    }
}
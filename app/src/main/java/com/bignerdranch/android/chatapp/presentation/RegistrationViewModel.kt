package com.bignerdranch.android.chatapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bignerdranch.android.chatapp.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class RegistrationViewModel : ViewModel() {

    companion object{
        const val TABLE_USERS = "users"
    }

    private var auth: FirebaseAuth = Firebase.auth
    private val firebaseDataBase = Firebase.database
    private val usersReference = firebaseDataBase.getReference(TABLE_USERS)

    init {
        auth.addAuthStateListener {
            user.value = it.currentUser
        }
    }

    val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData<String>()

    fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val firebaseUser = it.user
                if (firebaseUser == null){
                    return@addOnSuccessListener
                }
                val user = User(firebaseUser.uid,
                    firstName,
                    lastName,
                    age,
                    false)
                usersReference.child(user.id).setValue(user)
            }
            .addOnFailureListener {
                error.value = it.message
            }
    }
}
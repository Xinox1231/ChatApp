package com.bignerdranch.android.chatapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.database.*

class UsersViewModel : ViewModel() {

    companion object {
        const val TABLE_USERS = "users"
    }

    private val auth: FirebaseAuth = Firebase.auth
    private val firebaseDatabase: FirebaseDatabase
    private val usersReference: DatabaseReference

    val user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val users: MutableLiveData<ArrayList<User>> = MutableLiveData()

    init {
        auth.addAuthStateListener {
            user.value = it.currentUser
        }
        firebaseDatabase = Firebase.database
        usersReference = firebaseDatabase.getReference(TABLE_USERS)

        usersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser = auth.currentUser
                if (currentUser == null) return
                val usersList = ArrayList<User>()
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(User::class.java)
                    if (user == null) return
                    if (!user.id.equals(currentUser.uid)) {
                        usersList.add(user!!)
                    }
                }
                users.value = usersList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setUserOnline(isOnline: Boolean) {
        if (auth.uid == null) return
        usersReference.child(auth.uid!!).child(ChatViewModel.STATUS_KEY).setValue(isOnline)
    }

    fun logOut() {
        setUserOnline(false)
        auth.signOut()
    }
}
package com.bignerdranch.android.chatapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ChatViewModel(currentUserId: String, otherUserId: String) : ViewModel() {

    companion object {
        const val TABLE_USERS = "users"
        const val TABLE_MESSAGES = "messages"

        const val STATUS_KEY = "online"
    }

    val messages = MutableLiveData<List<Message>>()
    val otherUser = MutableLiveData<User>()
    val messageSent = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private val firebaseDataBase = Firebase.database
    private val usersReference = firebaseDataBase.getReference(TABLE_USERS)
    private val messagesReference = firebaseDataBase.getReference(TABLE_MESSAGES)

    var currentUserId: String
    var otherUserId: String

    init {
        this.currentUserId = currentUserId
        this.otherUserId = otherUserId
        //Найти другого пользователя
        usersReference.child(otherUserId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                otherUser.value = user
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        messagesReference
            .child(currentUserId)
            .child(otherUserId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = ArrayList<Message>()
                    for (dataSnapshot in snapshot.children) {
                        val message = dataSnapshot.getValue(Message::class.java)!!
                        messageList.add(message)
                    }
                    messages.value = messageList
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun setUserOnline(isOnline: Boolean) {
        usersReference.child(currentUserId).child(STATUS_KEY).setValue(isOnline)
    }

    fun sendMessage(message: Message) {
        messagesReference.child(message.senderId)
            .child(message.receiverId)
            .push()
            .setValue(message)
            .addOnSuccessListener {
                messagesReference.child(message.receiverId)
                    .child(message.senderId)
                    .push()
                    .setValue(message)
                    .addOnSuccessListener {
                        messageSent.value = true
                    }
                    .addOnFailureListener {
                        error.value = it.message
                    }
            }.addOnFailureListener {
                error.value = it.message
            }
    }
}
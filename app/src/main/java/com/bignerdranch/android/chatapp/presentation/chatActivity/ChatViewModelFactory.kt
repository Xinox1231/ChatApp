package com.bignerdranch.android.chatapp.presentation.chatActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory(currentUserId: String, otherUserId: String): ViewModelProvider.Factory {

    private val currentUserId: String
    private val otherUserId: String

    init {
        this.currentUserId = currentUserId
        this.otherUserId = otherUserId
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChatViewModel(currentUserId, otherUserId) as T
    }
}
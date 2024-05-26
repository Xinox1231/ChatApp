package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.chatapp.Message

interface ChatMessageItemWidget {
    val rootView: View
    fun bind(message: Message)

    fun setOnMessageClickListener(listener: (Message) -> Unit)
    fun setOnMessageLongClickListener(listener: (Message) -> Unit)
    interface Factory {
        fun create(parent: ViewGroup): ChatMessageItemWidget
    }
}
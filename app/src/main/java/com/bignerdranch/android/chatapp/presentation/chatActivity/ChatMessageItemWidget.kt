package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.content.Context
import com.bignerdranch.android.chatapp.Message

interface ChatMessageItemWidget {

    fun bind(message: Message)

    fun setOnMessageClickListener(listener: (Message) -> Unit)

    fun setOnMessageLongClickListener(listener: (Message) -> Unit)

    interface Factory {
        fun create(context: Context): ChatMessageItemWidget
    }
}
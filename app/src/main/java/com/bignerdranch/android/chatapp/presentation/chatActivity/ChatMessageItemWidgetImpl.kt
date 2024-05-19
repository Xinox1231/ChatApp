package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.bignerdranch.android.chatapp.Message
import com.bignerdranch.android.chatapp.R

class ChatMessageItemWidgetImpl(private val context: Context, val view: View) :
    ChatMessageItemWidget {

    private val tvMessage: TextView = view.findViewById(R.id.tvMessage)

    private var messageClickListener: ((Message) -> Unit)? = null
    private var messageLongClickListener: ((Message) -> Unit)? = null

    override fun bind(message: Message) {
        tvMessage.text = message.text

        view.setOnClickListener {
            messageClickListener?.invoke(message)
        }

        view.setOnLongClickListener {
            messageLongClickListener?.invoke(message)
            true
        }
    }

    override fun setOnMessageClickListener(listener: (Message) -> Unit) {
        messageClickListener = listener
    }

    override fun setOnMessageLongClickListener(listener: (Message) -> Unit) {
        messageLongClickListener = listener
    }

    class Factory : ChatMessageItemWidget.Factory {
        override fun create(context: Context): ChatMessageItemWidget {
            val view = LayoutInflater.from(context).inflate(
                R.layout.my_message_item,
                null,
                false
            )
            return ChatMessageItemWidgetImpl(context, view)
        }
    }
}
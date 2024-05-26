package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bignerdranch.android.chatapp.Message
import com.bignerdranch.android.chatapp.R

class MyChatMessageItemWidgetImpl(override val rootView: View) :
    ChatMessageItemWidget {

    private val tvMessage: TextView

    init {
        tvMessage = rootView.findViewById(R.id.tvMessage)
    }

    private var messageClickListener: ((Message) -> Unit)? = null
    private var messageLongClickListener: ((Message) -> Unit)? = null

    override fun bind(message: Message) {
        tvMessage.text = message.text

        rootView.setOnClickListener {
            messageClickListener?.invoke(message)
        }

        rootView.setOnLongClickListener {
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

    class Factory(val context: Context) : ChatMessageItemWidget.Factory {
        override fun create(parent: ViewGroup): ChatMessageItemWidget {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.my_message_item,
                parent,
                false
            )
            return MyChatMessageItemWidgetImpl(view)
        }
    }
}
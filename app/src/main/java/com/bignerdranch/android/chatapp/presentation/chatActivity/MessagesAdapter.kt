package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.chatapp.Message

class MessagesAdapter(
    private val factory: ChatMessageItemWidget.Factory
) :
    RecyclerView.Adapter<MessagesAdapter.ChatViewHolder>() {

    var messages : List<Message> = mutableListOf<Message>()
    inner class ChatViewHolder(private val chatMessageWidget: ChatMessageItemWidget) :
        RecyclerView.ViewHolder((chatMessageWidget as ChatMessageItemWidgetImpl).view) {
        fun bind(message: Message) {
            chatMessageWidget.bind(message)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val chatMessageWidget = factory.create(parent.context)
        return ChatViewHolder(chatMessageWidget)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}

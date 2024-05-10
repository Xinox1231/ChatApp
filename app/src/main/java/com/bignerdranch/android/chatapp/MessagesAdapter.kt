package com.bignerdranch.android.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MessagesAdapter(currentUserId: String) :
    RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    companion object {
        const val VIEW_TYPE_MY_MESSAGE = 0
        const val VIEW_TYPE_OTHER_MESSAGE = 1
    }

    var messages: List<Message> = ArrayList<Message>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    val currentUserId: String = currentUserId

    class MessageViewHolder(item: View) : ViewHolder(item) {
        val textViewMessage: TextView = item.findViewById(R.id.textViewMessage)
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return if (message.senderId == currentUserId) VIEW_TYPE_MY_MESSAGE
        else VIEW_TYPE_OTHER_MESSAGE

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutResId: Int = if (viewType == VIEW_TYPE_MY_MESSAGE) R.layout.my_message_item
        else R.layout.other_message_item
        val view = LayoutInflater.from(parent.context)
            .inflate(layoutResId, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.textViewMessage.setText(message.text)
    }
}
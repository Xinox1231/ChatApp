package com.bignerdranch.android.chatapp.presentation.chatActivity

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.chatapp.Message

class MessagesAdapter(
    private val currentUserId: String,
    private val currentUserMessageItemFactory: ChatMessageItemWidget.Factory,
    private val otherUserMessageItemFactory: ChatMessageItemWidget.Factory,
) :
    RecyclerView.Adapter<MessageViewHolder>() {
    companion object {
        const val MY_MESSAGE_VIEW_TYPE = 0
        const val OTHER_MESSAGE_VIEW_TYPE = 10
    }

    var items: List<Message> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var counter = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val widget = if (viewType == MY_MESSAGE_VIEW_TYPE) {
            currentUserMessageItemFactory.create(parent)
        } else {
            otherUserMessageItemFactory.create(parent)
        }
        counter++
        Log.d("MessagesAdapter", counter.toString())
        return MessageViewHolder(widget)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position].senderId == currentUserId) MY_MESSAGE_VIEW_TYPE
        else OTHER_MESSAGE_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.widget.bind(items[position])

    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class MessageViewHolder(val widget: ChatMessageItemWidget) : RecyclerView.ViewHolder(widget.rootView)

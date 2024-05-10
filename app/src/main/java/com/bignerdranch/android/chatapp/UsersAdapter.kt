package com.bignerdranch.android.chatapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class UsersAdapter() : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    var users = ArrayList<User>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    lateinit var onUserClickListener: OnUserClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent,
            false
        )
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        val userInfo = String.format(
            "%s %s, %s",
            user.name,
            user.lastName,
            user.age
        )
        val backgroundId : Int = if (user.online) R.drawable.circle_green
        else R.drawable.circle_red
        val drawableBackground   = ContextCompat.getDrawable(holder.itemView.context, backgroundId)
        holder.onlineStatus.background = drawableBackground
        holder.textViewUserInfo.text = userInfo
        holder.itemView.setOnClickListener{
            if(onUserClickListener != null){
                onUserClickListener.onUserClick(user)
            }
        }
    }

    class UserViewHolder(item: View) : ViewHolder(item) {
        var textViewUserInfo: TextView = item.findViewById(R.id.textViewUserInfo)
        val onlineStatus: View = item.findViewById(R.id.onlineStatus)
    }
    interface OnUserClickListener{
        fun onUserClick(user: User)
    }
}
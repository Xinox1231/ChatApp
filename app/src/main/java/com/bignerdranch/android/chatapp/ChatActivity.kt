package com.bignerdranch.android.chatapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ChatActivity"

        const val EXTRA_CURRENT_USER_ID = "current_id"
        const val EXTRA_OTHER_USER_ID = "other_id"

        fun newIntent(context: Context, currentUserId: String, otherUserId: String): Intent {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId)
            return intent
        }
    }

    private lateinit var textViewTitle: TextView
    private lateinit var onlineStatus: View
    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var editTextMessaage: EditText
    private lateinit var imageViewSendMessage: ImageView

    private lateinit var messagesAdapter: MessagesAdapter

    private lateinit var currentUserId: String
    private lateinit var otherUserId: String

    private lateinit var viewModel: ChatViewModel
    private lateinit var chatViewModelFactory: ChatViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initViews()

        currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID)!!
        otherUserId = intent.getStringExtra(EXTRA_OTHER_USER_ID)!!

        chatViewModelFactory = ChatViewModelFactory(currentUserId, otherUserId)
        viewModel = ViewModelProvider(this, chatViewModelFactory)[ChatViewModel::class.java]

        messagesAdapter = MessagesAdapter(currentUserId)
        recyclerViewMessages.adapter = messagesAdapter

        observeViewModel()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.setUserOnline(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setUserOnline(false)
    }

    private fun initViews() {
        textViewTitle = findViewById(R.id.textViewTitle)
        onlineStatus = findViewById(R.id.onlineStatus)
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages)
        editTextMessaage = findViewById(R.id.editTextMessage)
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage)
    }

    private fun setupClickListeners() {
        imageViewSendMessage.setOnClickListener {
            val message = Message(
                editTextMessaage.text.trim().toString(),
                currentUserId,
                otherUserId
            )
            viewModel.sendMessage(message)
        }
    }

    private fun observeViewModel() {
        viewModel.messages.observe(this) {
            messagesAdapter.messages = it
        }
        viewModel.error.observe(this) {
            if (it != null) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.messageSent.observe(this) { sent ->
            if (sent) editTextMessaage.text.clear()
        }
        viewModel.otherUser.observe(this) { user ->
            Log.d(TAG, user.name)
            val stringUserInfo = String.format("%s %s", user.name, user.lastName)
            textViewTitle.text = stringUserInfo
            val backgroundId: Int = if (user.online) R.drawable.circle_green
            else R.drawable.circle_red
            val drawableBackground = ContextCompat.getDrawable(this@ChatActivity, backgroundId)
            onlineStatus.background = drawableBackground
        }
    }

}
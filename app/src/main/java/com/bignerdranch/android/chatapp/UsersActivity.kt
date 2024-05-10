package com.bignerdranch.android.chatapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class UsersActivity : AppCompatActivity() {

    companion object {
        const val TAG = "UsersActivity"
        const val EXTRA_CURRENT_USER_ID = "current_id"

        fun newIntent(context: Context, currentUserId: String): Intent {
            val intent = Intent(context, UsersActivity::class.java)
            intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId)
            return intent
        }
    }

    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var usersAdapter: UsersAdapter

    private lateinit var viewModel: UsersViewModel
    private lateinit var currentUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        initViews()

        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        observeViewModel()
        currentUserId = intent.getStringExtra(EXTRA_CURRENT_USER_ID)!!
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
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers)
        usersAdapter = UsersAdapter()
        recyclerViewUsers.adapter = usersAdapter
    }

    private fun observeViewModel() {
        viewModel.user.observe(this) {
            if (it == null) {
                val intent = LoginActivity.newIntent(this)
                viewModel.setUserOnline(false)
                startActivity(intent)
                finish()
            }
        }
        viewModel.users.observe(this) {
            usersAdapter.users = it
        }
    }

    private fun setupClickListeners() {
        usersAdapter.onUserClickListener = (object : UsersAdapter.OnUserClickListener {
            override fun onUserClick(user: User) {
                startActivity(
                    ChatActivity.newIntent(
                        this@UsersActivity,
                        currentUserId,
                        user.id
                    )
                )
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_logout) {
            viewModel.logOut()
        }
        return super.onOptionsItemSelected(item)
    }
}
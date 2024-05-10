package com.bignerdranch.android.chatapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonSignIn: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var textViewRegister: TextView

    private lateinit var viewModel: LoginViewModel

    companion object {
        const val TAG = "MainActivity"

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        observeViewModel()
        setupClickListeners()

    }

    private fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonSignIn = findViewById(R.id.buttonLogin)
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword)
        textViewRegister = findViewById(R.id.textViewRegister)
    }

    private fun observeViewModel() {
        viewModel.user.observe(this) { fireBaseUser ->
            if (fireBaseUser != null) {
                val intent = UsersActivity.newIntent(this, fireBaseUser.uid)
                startActivity(intent)
                finish()
            }
        }
        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(
                    this,
                    errorMessage,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun setupClickListeners() {
        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.trim().toString()
            val password = editTextPassword.text.trim().toString()
            viewModel.signIn(email = email, password = password)
        }
        textViewForgotPassword.setOnClickListener {
            startActivity(
                ResetPasswordActivity.newIntent(
                    this,
                    editTextEmail.text.toString()
                )
            )
        }
        textViewRegister.setOnClickListener {
            startActivity(RegistrationActivity.newIntent(this))
        }
    }
}
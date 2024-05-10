package com.bignerdranch.android.chatapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class ResetPasswordActivity : AppCompatActivity() {

    companion object{

        const val EXTRA_EMAIL = "email"

        fun newIntent(context: Context, email: String): Intent{
            val intent = Intent(context, ResetPasswordActivity::class.java)
            intent.putExtra(EXTRA_EMAIL, email)
            return intent
        }
    }

    private lateinit var editTextEmail: EditText
    private lateinit var buttonResetPassword: Button

    private lateinit var viewModel: ResetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        initViews()

        viewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        observeViewModel()

        val email = intent.getStringExtra(EXTRA_EMAIL)
        editTextEmail.setText(email)

        buttonResetPassword.setOnClickListener{
            val email: String = editTextEmail.text.toString().trim()
            viewModel.resetPassword(email)
        }
    }

    private fun initViews(){
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonResetPassword = findViewById(R.id.buttonResetPassword)
    }

    private fun observeViewModel(){
        viewModel.isSuccess.observe(this){
            if(it == true){
                Toast.makeText(this,
                    getString(R.string.reset_link_sent),
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        viewModel.error.observe(this){
            if(it != null) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}
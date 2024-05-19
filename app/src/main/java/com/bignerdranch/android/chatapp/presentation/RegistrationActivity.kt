package com.bignerdranch.android.chatapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bignerdranch.android.chatapp.R

class RegistrationActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegistrationActivity::class.java)
        }
    }

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var buttonSignUp: Button

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initViews()

        viewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
        observeViewModel()
        setupClickListeners()

    }

    fun initViews() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextFirstName = findViewById(R.id.editTextFirstName)
        editTextLastName = findViewById(R.id.editTextLastName)
        editTextAge = findViewById(R.id.editTextAge)
        buttonSignUp = findViewById(R.id.buttonSignUp)
    }

    fun getTrimmedValue(editText: EditText): String {
        return editText.text.trim().toString()
    }

    fun observeViewModel() {
        viewModel.user.observe(this){firebaseUser ->
            if(firebaseUser != null) {
                val intent = UsersActivity.newIntent(this@RegistrationActivity, firebaseUser.uid)
                startActivity(intent)
                finish()
            }
        }
        viewModel.error.observe(this) {
            if (it != null) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun setupClickListeners(){
        buttonSignUp.setOnClickListener {
            val email = getTrimmedValue(editTextEmail)
            val password = getTrimmedValue(editTextPassword)
            val firstName = getTrimmedValue(editTextFirstName)
            val lastName = getTrimmedValue(editTextLastName)
            val age: Int = Integer.parseInt(getTrimmedValue(editTextAge))
            viewModel.signUp(email, password, firstName, lastName, age)
        }
    }
}
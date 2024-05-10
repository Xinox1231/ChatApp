package com.bignerdranch.android.chatapp

data class User(
    val id: String = "",
    val name: String = "",
    val lastName: String = "",
    val age: Int = 0,
    val online: Boolean = false
)
package com.example.todolist

data class Item(
    var id: Int,
    val title: String,
    val description: String,
    val done: Boolean
)

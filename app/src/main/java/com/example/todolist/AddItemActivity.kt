package com.example.todolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
    }

    fun click(view: View)
    {
        val newName = findViewById<EditText>(R.id.newName).text.toString()
        val newDescription = findViewById<EditText>(R.id.newDescription).text.toString()

        if(newName.isBlank() || newDescription.isBlank())
            return

        val myintent = Intent()
        myintent.putExtra("name", newName)
        myintent.putExtra("description", newDescription)
        setResult(Activity.RESULT_OK, myintent)
        finish()
    }
}
package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import todolist.ItemsQueries

class MainActivity : AppCompatActivity() {
    lateinit var items: ArrayList<Item>
    lateinit var adapter: CustomAdapter
    lateinit var itemsQueries: ItemsQueries

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val driver = AndroidSqliteDriver(Database.Schema, this, "items.db")
        val database = Database(driver)

        itemsQueries = database.itemsQueries

        items = ArrayList()

        for(item in itemsQueries.selectAll().executeAsList())
        {
            items.add(Item(item.id.toInt(), item.name, item.description, item.done == 1L))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(items)
        recyclerView.adapter = adapter
    }

    fun click(view: View)
    {
        val myintent = Intent(this, AddItemActivity::class.java)
        resultLauncher.launch(myintent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    {
        result -> val data = result.data
        val newName = data?.getStringExtra("name")
        val newDescription = data?.getStringExtra("description")
        if(newName != null && newDescription != null)
            add(Item(1, newName, newDescription, false))
    }

    fun add(item: Item)
    {
        items.add(item)
        val size = items.size

        if(size > 1)
            item.id = items.get(size - 2).id + 1

        itemsQueries.insert(item.id.toLong(), item.title, item.description, if (item.done) 1 else 0)

        adapter.notifyItemInserted(size)
    }


    fun delete(id: Int)
    {
        itemsQueries.delete(id.toLong())
        for(i in 0 until items.size)
        {
            if(items.get(i).id == id)
            {
                items.removeAt(i)
                adapter.notifyItemRemoved(i)
                return
            }
        }
    }

    fun mark(id: Int, checked: Boolean)
    {
        itemsQueries.mark(if (checked) 1 else 0, id.toLong())
    }
}
package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dataSet: List<Item>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: Int = 0
        val name: TextView
        val description: TextView
        val done: CheckBox
        val v: View

        init {
            name = view.findViewById(R.id.name)
            description = view.findViewById(R.id.description)
            done = view.findViewById(R.id.done)
            v = view
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val str = dataSet[position].id.toString() + ". " + dataSet[position].title
        viewHolder.id = dataSet[position].id
        viewHolder.name.text = str
        viewHolder.description.text = dataSet[position].description
        viewHolder.done.isChecked = dataSet[position].done

        viewHolder.v.setOnLongClickListener {
            (viewHolder.v.context as MainActivity).delete(viewHolder.id)
            true
        }

        viewHolder.done.setOnCheckedChangeListener { _, b ->
            (viewHolder.v.context as MainActivity).mark(viewHolder.id, b)
        }
    }

    override fun getItemCount() = dataSet.size

}
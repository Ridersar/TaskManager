package com.example.taskmanager


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val mList: List<ItemsViewModel>, val listener: Listener) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = mList[position]

        holder.itemView.setOnClickListener {
            listener.onClick(itemsViewModel.id) //TODO передавать только ID, а потом по id получать заново
        }


        if (itemsViewModel.isDone)
            holder.imageView.setImageResource(R.drawable.success)
        else
            holder.imageView.setImageResource(R.drawable.failure)

        holder.textView.text = itemsViewModel.text
        holder.descriptionView.text = itemsViewModel.description
        holder.dateTimeView.text = itemsViewModel.dateTime
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val descriptionView: TextView = itemView.findViewById(R.id.descriptionView)
        val dateTimeView: TextView = itemView.findViewById(R.id.dateTimeView)
    }

    interface Listener {
        fun onClick(id: Int)
    }
}
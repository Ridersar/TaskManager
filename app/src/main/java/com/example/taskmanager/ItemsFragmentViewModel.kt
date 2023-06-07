package com.example.taskmanager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class ItemsFragmentViewModel (
    private val db: AppDatabase
) : ViewModel() {

    val transactions = db.itemDao().getAll()

    fun insertTransaction(
        title: String,
        description: String,
        isDone: Boolean,
        creation_date: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val transaction = Item(
                uid = null,
                title = title,
                description = description,
                isDone = isDone,
                creation_date = null //todo
            )
            var item = Item(null, title, description, isDone, creation_date)
            val id = db.itemDao().insertItem(item)
        }
    }
}
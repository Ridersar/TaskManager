package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAll(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE isDone = 1")
    fun getAllDone(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE isDone = 0")
    fun getAllUndone(): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE uid = :id")
    fun getById(id: Long): Item

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Query("UPDATE item SET isDone = 1 WHERE uid = :id")
    fun doneItem(id: Int)
}
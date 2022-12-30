package com.example.todolist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.data.db.room.TodoDao
import com.example.todolist.domain.model.Todo

@Database(entities = [Todo::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

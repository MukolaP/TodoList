package com.example.todolist.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dataTodo")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "isAccept") var isAccept: Boolean = false,
    @ColumnInfo(name = "isChecked") var isChecked: Boolean = false,
)
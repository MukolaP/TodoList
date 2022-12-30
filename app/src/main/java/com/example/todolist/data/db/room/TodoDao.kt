package com.example.todolist.data.db.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todolist.domain.model.Todo

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM dataTodo")
    fun getAll(): LiveData<List<Todo>>

    @Query("DELETE FROM dataTodo")
    suspend fun deleteAll()

    @Query("DELETE FROM dataTodo WHERE isChecked=:isChecked")
    suspend fun deleteOnlyElect(isChecked: Boolean = true)

    @Delete
    suspend fun delete(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(todo: Todo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOnlySelect(todo: List<Todo>)

    @Query("SELECT * FROM dataTodo WHERE isChecked=:isChecked")
    fun getOnlySelect(isChecked: Boolean = true): LiveData<List<Todo>>

    @Query("SELECT * FROM dataTodo WHERE isAccept=:isDelete")
    fun getOnlyPerformed(isDelete: Boolean = true): LiveData<List<Todo>>

    @Query("SELECT * FROM dataTodo WHERE isAccept=:isDelete")
    fun getOnlyNotFulfilled(isDelete: Boolean = false): LiveData<List<Todo>>
}

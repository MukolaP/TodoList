package com.example.todolist.domain.interactor

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.todolist.data.db.AppDatabase
import com.example.todolist.domain.model.Todo
import javax.inject.Inject

interface MainInteractor {

    val todo: LiveData<List<Todo>>

    val performed: LiveData<List<Todo>>

    val notFulfilled: LiveData<List<Todo>>

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

    suspend fun deleteAll()

    class Base @Inject constructor(db: AppDatabase) : MainInteractor {

        private val dao = db.todoDao()

        override val todo = dao.getAll()

        override val performed = dao.getOnlyPerformed()

        override val notFulfilled = dao.getOnlyNotFulfilled()

        override suspend fun update(todo: Todo) = dao.update(todo = todo)

        override suspend fun delete(todo: Todo) = dao.delete(todo = todo)

        override suspend fun deleteAll() = dao.deleteAll()
    }
}
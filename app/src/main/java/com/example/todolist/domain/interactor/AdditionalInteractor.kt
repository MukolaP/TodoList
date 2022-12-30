package com.example.todolist.domain.interactor

import androidx.lifecycle.LiveData
import com.example.todolist.data.db.AppDatabase
import com.example.todolist.domain.model.Todo
import javax.inject.Inject

interface AdditionalInteractor {
    val todo: LiveData<List<Todo>>

    val performed: LiveData<List<Todo>>

    val notFulfilled: LiveData<List<Todo>>

    val select: LiveData<List<Todo>>

    suspend fun update(todo: Todo)

    suspend fun deleteOnlyElect()

    suspend fun updateOnlyElect(todo: List<Todo>)

    class Base @Inject constructor(db: AppDatabase): AdditionalInteractor {

        private val dao = db.todoDao()

        override val todo = dao.getAll()

        override val performed = dao.getOnlyPerformed()

        override val notFulfilled = dao.getOnlyNotFulfilled()

        override val select: LiveData<List<Todo>> = dao.getOnlySelect()

        override suspend fun update(todo: Todo) = dao.update(todo)

        override suspend fun deleteOnlyElect() = dao.deleteOnlyElect()

        override suspend fun updateOnlyElect(todo: List<Todo>) = dao.updateOnlySelect(todo)
    }
}
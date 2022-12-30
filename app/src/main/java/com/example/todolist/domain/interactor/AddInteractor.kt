package com.example.todolist.domain.interactor

import com.example.todolist.data.db.AppDatabase
import com.example.todolist.domain.model.Todo
import javax.inject.Inject

interface AddInteractor {

    suspend fun insert(todo: Todo)

    class Base @Inject constructor(db: AppDatabase) : AddInteractor {
        private val dao = db.todoDao()

        override suspend fun insert(todo: Todo) = dao.insert(todo)
    }
}
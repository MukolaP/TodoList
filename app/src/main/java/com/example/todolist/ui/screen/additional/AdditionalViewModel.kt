package com.example.todolist.ui.screen.additional

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.interactor.AdditionalInteractor
import com.example.todolist.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class AdditionalViewModel @Inject constructor(
    private val interactor: AdditionalInteractor, private val dispatcher: CoroutineContext
) : ViewModel() {

    val todo = interactor.todo

    fun content(filter: Int) = when (filter) {
        0 -> todo
        1 -> performed
        2 -> notFulfilled
        else -> throw IllegalArgumentException("Filter not found")
    }

    fun update(todo: Todo) = viewModelScope.launch(dispatcher) {
        interactor.update(todo)
    }

    fun deleteOnlyElect() = viewModelScope.launch(dispatcher) {
        interactor.deleteOnlyElect()
    }

    fun updateOnlyElect(todo: List<Todo>) = viewModelScope.launch(dispatcher) {
        todo.map {
            it.isAccept = true
            it.isChecked = false
        }

        interactor.updateOnlyElect(todo)
    }

    fun taskTextStyle(isDelete: Boolean): TextDecoration = if (isDelete) TextDecoration.LineThrough
    else TextDecoration.None

    fun backgroundTaskItem(isDelete: Boolean, first: Color, second: Color) =
        if (isDelete) second else first

    val select = interactor.select

    private val performed = interactor.performed

    private val notFulfilled = interactor.notFulfilled
}
package com.example.todolist.ui.screen.main

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.app.colorTheme
import com.example.todolist.domain.interactor.MainInteractor
import com.example.todolist.domain.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val interactor: MainInteractor, private val dispatcher: CoroutineContext
) : ViewModel() {

    fun content(filter: Int) = when (filter) {
        0 -> todo
        1 -> performed
        2 -> notFulfilled
        else -> throw IllegalArgumentException("Filter not found")
    }

    val todo = interactor.todo

    fun acceptPress(id: Int, text: String) = update(Todo(id = id, text = text, isAccept = true))

    fun deletePress(todo: Todo) = viewModelScope.launch(dispatcher) {
        interactor.delete(todo)
    }

    fun deleteAllPress() = viewModelScope.launch(dispatcher) {
        interactor.deleteAll()
    }

    fun taskTextStyle(isDelete: Boolean): TextDecoration = if (isDelete) TextDecoration.LineThrough
    else TextDecoration.None

    fun backgroundTaskItem(isDelete: Boolean, first: Color, second: Color) =
        if (isDelete) second else first

    fun acceptEnabled(isAccept: Boolean) = !isAccept

    fun drawableColorAccept(isAccept: Boolean, first: Color, second: Color) =
        if (isAccept) second else first

    fun changeThemePressed() {
        colorTheme.value = !colorTheme.value
    }

    fun changeThemeIcon(first: Int, second: Int) = if (colorTheme.value) first else second

    private val performed = interactor.performed

    private val notFulfilled = interactor.notFulfilled

    private fun update(todo: Todo) = viewModelScope.launch(dispatcher) {
        interactor.update(todo)
    }
}
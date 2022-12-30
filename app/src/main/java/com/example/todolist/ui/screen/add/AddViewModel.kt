package com.example.todolist.ui.screen.add

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todolist.domain.interactor.AddInteractor
import com.example.todolist.domain.model.Todo
import com.example.todolist.navigation.NavigationTree
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class AddViewModel @Inject constructor(
    private val interactor: AddInteractor,
    private val dispatcher: CoroutineContext,
    private val context: Context,
) : ViewModel() {

    private fun add(todo: Todo) {
        viewModelScope.launch(dispatcher) {
            interactor.insert(todo)
        }
    }

    fun addPress(navController: NavController, task: String) {
        if (task.isNotEmpty()) {
            add(Todo(text = task))
            navController.navigate(NavigationTree.Main.name)
        } else Toast.makeText(context, EMPTY_STRING, Toast.LENGTH_LONG).show()
    }

    private companion object {

        const val EMPTY_STRING = "Please enter task"
    }
}
package com.example.todolist.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.todolist.ui.screen.ApplicationScreen
import com.example.todolist.ui.theme.AppTheme
import com.example.todolist.ui.theme.TodoListTheme
import dagger.hilt.android.AndroidEntryPoint

val colorTheme = mutableStateOf(true)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme(colorTheme = colorTheme.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppTheme.colors.primaryBackground)
                ) {
                    ApplicationScreen()
                }
            }
        }
    }
}
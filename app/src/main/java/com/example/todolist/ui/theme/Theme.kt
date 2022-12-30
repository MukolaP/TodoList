package com.example.todolist.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun TodoListTheme(
    colorTheme: Boolean, content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        if (colorTheme) LocalColorProvider provides lightPalette
        else LocalColorProvider provides darkPalette, content = content
    )
}

object AppTheme {

    val colors: Colors
        @Composable @ReadOnlyComposable get() = LocalColorProvider.current
}

val LocalColorProvider = staticCompositionLocalOf<Colors> {
    error("No default colors provided")
}
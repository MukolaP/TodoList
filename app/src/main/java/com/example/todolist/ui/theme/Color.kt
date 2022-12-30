package com.example.todolist.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val primaryBackground: Color,
    val secondaryBackground: Color,
    val primaryButton: Color,
    val primaryText: Color,
    val primaryButtonText: Color,
    val primaryHint: Color,
    val primaryTextFieldBackground: Color,
    val disabledButton: Color,
    val bottomBar: Color,
)

val lightPalette = Colors(
    primaryBackground = Color.White,
    secondaryBackground = Color.Gray,
    primaryButton = Color(0xFF6200EE),
    primaryText = Color.Black,
    primaryButtonText = Color.White,
    primaryHint = Color.Gray,
    primaryTextFieldBackground = Color.LightGray,
    disabledButton = Color.DarkGray,
    bottomBar = Color(0xFF6200EE),
)

val darkPalette = Colors(
    primaryBackground = Color.Black,
    secondaryBackground = Color.DarkGray,
    primaryButton = Color.White,
    primaryText = Color.White,
    primaryButtonText = Color.Black,
    primaryHint = Color.Gray,
    primaryTextFieldBackground = Color.White,
    disabledButton = Color.Gray,
    bottomBar = Color(0xFF6200EE),
)

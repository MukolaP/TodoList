package com.example.todolist.ui.screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.navigation.NavigationTree
import com.example.todolist.ui.screen.add.AddScreen
import com.example.todolist.ui.screen.add.AddViewModel
import com.example.todolist.ui.screen.additional.AdditionalScreen
import com.example.todolist.ui.screen.additional.AdditionalViewModel
import com.example.todolist.ui.screen.main.MainScreen
import com.example.todolist.ui.screen.main.MainViewModel

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Main.name) {
        composable(NavigationTree.Main.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(viewModel = mainViewModel, navController = navController)
        }
        composable(NavigationTree.Add.name) {
            val addViewModel = hiltViewModel<AddViewModel>()
            AddScreen(viewModel = addViewModel, navController = navController)
        }
        composable(NavigationTree.Additional.name) {
            val additionalViewModel = hiltViewModel<AdditionalViewModel>()
            AdditionalScreen(viewModel = additionalViewModel, navController = navController)
        }
    }
}
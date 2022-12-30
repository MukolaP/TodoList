package com.example.todolist.ui.screen.add

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.navigation.NavigationTree
import com.example.todolist.ui.theme.AppTheme.colors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(viewModel: AddViewModel, navController: NavController) {
    Scaffold(topBar = { AddTopBar(navController = navController) }) {
        AddBody(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun AddTopBar(navController: NavController) {
    val backIcon = R.drawable.ic_round_arrow_back_ios_24

    TopAppBar(
        title = { Text(text = "Add task", color = colors.primaryText) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(NavigationTree.Main.name)
            }) {
                Icon(
                    painterResource(id = backIcon),
                    modifier = Modifier,
                    contentDescription = "back",
                    tint = colors.primaryButton
                )
            }
        },
        backgroundColor = colors.primaryBackground,
        contentColor = colors.primaryBackground,
        elevation = 12.dp,
    )
}

@Composable
fun AddBody(navController: NavController, viewModel: AddViewModel) {
    val saveIcon = R.drawable.ic_baseline_save_24

    var task: String by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
    ) {
        Spacer(modifier = Modifier.padding(top = 28.dp))

        TextField(
            value = task,
            onValueChange = {
                task = it
            },
            label = {
                Text(text = "New task", color = colors.primaryHint)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = MaterialTheme.shapes.small.copy(
                bottomEnd = CornerSize(50),
                bottomStart = CornerSize(50),
                topStart = CornerSize(50),
                topEnd = CornerSize(50)
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colors.primaryTextFieldBackground)
        )

        Spacer(modifier = Modifier.weight(1F))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = {
                    viewModel.addPress(navController = navController, task = task)
                }, modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .padding(bottom = 10.dp)
            ) {

                Icon(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = painterResource(id = saveIcon),
                    tint = colors.primaryButton,
                    contentDescription = "add task"
                )
            }

        }

    }
}
package com.example.todolist.ui.screen.additional

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.domain.model.Todo
import com.example.todolist.navigation.NavigationTree
import com.example.todolist.ui.screen.main.main_filter
import com.example.todolist.ui.theme.AppTheme.colors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AdditionalScreen(viewModel: AdditionalViewModel, navController: NavController) {
    Scaffold(topBar = { AdditionalTopBar(navController = navController, viewModel = viewModel) }) {
        AdditionalBody(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun AdditionalTopBar(navController: NavController, viewModel: AdditionalViewModel) {
    val backIcon = R.drawable.ic_round_arrow_back_ios_24
    val deleteIcon = R.drawable.ic_baseline_delete_outline_24
    val acceptIcon = R.drawable.ic_baseline_check_circle_outline_24

    val selectTodo = viewModel.select.observeAsState()

    TopAppBar(
        title = { Text(text = "Select task", color = colors.primaryText) },
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
        actions = {
            Row(Modifier.fillMaxWidth()) {
                IconButton(onClick = {
                    viewModel.deleteOnlyElect()
                }) {
                    Icon(
                        painterResource(id = deleteIcon),
                        modifier = Modifier,
                        contentDescription = "back",
                        tint = colors.primaryButton
                    )
                }

                IconButton(onClick = {
                    viewModel.updateOnlyElect(selectTodo.value!!)
                }) {
                    Icon(
                        painterResource(id = acceptIcon),
                        modifier = Modifier,
                        contentDescription = "back",
                        tint = colors.primaryButton
                    )
                }
            }
        },
        backgroundColor = colors.primaryBackground,
        contentColor = colors.primaryBackground,
        elevation = 12.dp,
    )
}

@Composable
fun AdditionalBody(viewModel: AdditionalViewModel, navController: NavController) {
    val content: State<List<Todo>?> = viewModel.content(main_filter).observeAsState()

    Column {
        TodoList(
            content = content,
            viewModel = viewModel,
            modifier = Modifier
                .weight(1F)
                .padding(bottom = 66.dp)
                .height(48.dp),
            navController = navController
        )
    }
}

@Composable
fun TodoList(
    content: State<List<Todo>?>,
    viewModel: AdditionalViewModel,
    modifier: Modifier,
    navController: NavController
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        item {
            content.value?.reversed()
                ?.map { TodoListItem(it, viewModel = viewModel, navController = navController) }
        }
    }
}

@Composable
fun TodoListItem(item: Todo, viewModel: AdditionalViewModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = viewModel.backgroundTaskItem(
            isDelete = item.isAccept,
            first = colors.primaryBackground,
            second = colors.secondaryBackground
        ),
        shape = MaterialTheme.shapes.small.copy(CornerSize(100))
    ) {
        TodoListItemView(
            viewModel = viewModel, item = item
        )
    }
}

@Composable
fun TodoListItemView(
    viewModel: AdditionalViewModel, item: Todo
) {
    val isChecked = remember { mutableStateOf(item.isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = checkNotNull(value = item.text),
            modifier = Modifier
                .weight(1F)
                .padding(start = 50.dp)
                .clickable(true) { item.isChecked != !item.isChecked },
            color = colors.primaryText,
            fontSize = 16.sp,
            style = LocalTextStyle.current.copy(
                textDecoration = viewModel.taskTextStyle(isDelete = item.isAccept)
            )
        )

        Checkbox(checked = item.isChecked, onCheckedChange = {
            isChecked.value = !isChecked.value
            viewModel.update(
                Todo(
                    id = item.id,
                    text = item.text,
                    isAccept = item.isAccept,
                    isChecked = isChecked.value
                )
            )
        })
    }
}
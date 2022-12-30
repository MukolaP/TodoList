@file:OptIn(ExperimentalMaterialApi::class)

package com.example.todolist.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.R
import com.example.todolist.domain.model.Todo
import com.example.todolist.navigation.NavigationTree
import com.example.todolist.ui.theme.AppTheme.colors
import kotlin.math.roundToInt

var main_filter: Int = 0
var content: State<List<Todo>?> = mutableStateOf(listOf(Todo()))

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel, navController: NavController) {
    content = viewModel.content(main_filter).observeAsState()

    Scaffold(topBar = { MainTopBar(viewModel = viewModel, navController = navController) },
        bottomBar = { MainBottomBar(navController = navController, viewModel = viewModel) }) {
        MainBody(viewModel = viewModel, navController = navController, content = content)
    }
}

@Composable
fun MainTopBar(viewModel: MainViewModel, navController: NavController) {
    var expanded: Boolean by rememberSaveable { mutableStateOf(false) }
    val settingsIcon = R.drawable.ic_baseline_more_vert_24

    TopAppBar(
        title = { Text(text = "Tasks", color = colors.primaryText) },
        actions = {
            Row {

                IconButton(
                    onClick = { expanded = true },
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(40.dp)
                        .height(40.dp)
                ) {

                    Icon(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .width(40.dp)
                            .height(40.dp),
                        painter = painterResource(id = settingsIcon),
                        tint = colors.primaryButton,
                        contentDescription = "Send message"
                    )
                }
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = {
                    main_filter = 0
                    navController.navigate(NavigationTree.Main.name)
                    expanded = false
                }) {
                    Text("Show all")
                }

                DropdownMenuItem(onClick = {
                    main_filter = 1
                    navController.navigate(NavigationTree.Main.name)
                    expanded = false
                }) {
                    Text("Show only performed")
                }

                DropdownMenuItem(onClick = {
                    main_filter = 2
                    navController.navigate(NavigationTree.Main.name)
                    expanded = false
                }) {
                    Text("Show only not fulfilled")
                }

                content = viewModel.content(main_filter).observeAsState()
            }
        },
        backgroundColor = colors.primaryBackground,
        contentColor = colors.primaryBackground,
        elevation = 12.dp,
    )
}

@Composable
fun MainBottomBar(navController: NavController, viewModel: MainViewModel) {
    val addIcon = R.drawable.ic_round_add_24
    val deleteAllIcon = R.drawable.ic_round_delete_sweep_24
    val darkThemeIcon = R.drawable.ic_round_nightlight_round_24
    val lightThemeIcon = R.drawable.ic_round_wb_sunny_24

    val openDialog = remember { mutableStateOf(false) }

    BottomAppBar(backgroundColor = colors.bottomBar, elevation = 12.dp) {
        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { openDialog.value = true },
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .weight(1F)
            ) {

                Icon(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp),
                    painter = painterResource(id = deleteAllIcon),
                    tint = colors.primaryButtonText,
                    contentDescription = "deleteAll"
                )
            }

            IconButton(
                onClick = { viewModel.changeThemePressed() },
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .weight(1F)
            ) {

                Icon(
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp), painter = painterResource(
                        id = viewModel.changeThemeIcon(
                            first = lightThemeIcon, second = darkThemeIcon
                        )
                    ), tint = colors.primaryButtonText, contentDescription = "change theme"
                )
            }

            IconButton(
                onClick = { navController.navigate(NavigationTree.Add.name) },
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .weight(1F)
            ) {

                Icon(
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    painter = painterResource(id = addIcon),
                    tint = colors.primaryButtonText,
                    contentDescription = "add"
                )
            }

            if (openDialog.value) {

                AlertDialog(onDismissRequest = {
                    openDialog.value = false
                }, title = {
                    Text(text = "Delete all")
                }, text = {
                    Text("Are you sure you want to delete all task")
                }, confirmButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                            viewModel.deleteAllPress()
                        }, shape = MaterialTheme.shapes.small.copy(
                            bottomEnd = CornerSize(50),
                            bottomStart = CornerSize(50),
                            topStart = CornerSize(50),
                            topEnd = CornerSize(50)
                        )
                    ) {
                        Text("Confirm")
                    }
                }, dismissButton = {
                    Button(

                        onClick = {
                            openDialog.value = false
                        }, shape = MaterialTheme.shapes.small.copy(
                            bottomEnd = CornerSize(50),
                            bottomStart = CornerSize(50),
                            topStart = CornerSize(50),
                            topEnd = CornerSize(50)
                        )
                    ) {
                        Text("Dismiss")
                    }
                })
            }
        }
    }
}

@Composable
fun MainBody(navController: NavController, viewModel: MainViewModel, content: State<List<Todo>?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.primaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        content.value?.let {
            TodoList(
                content = it,
                viewModel = viewModel,
                modifier = Modifier
                    .weight(1F)
                    .padding(bottom = 66.dp)
                    .height(48.dp),
                navController = navController
            )
        }

    }
}

@Composable
fun TodoList(
    content: List<Todo>, viewModel: MainViewModel, modifier: Modifier, navController: NavController
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        item {
            content.reversed()
                .map { TodoListItem(it, viewModel = viewModel, navController = navController) }
        }
    }
}

@Composable
fun TodoListItem(item: Todo, viewModel: MainViewModel, navController: NavController) {
    val squareSize = 48.dp
    val swipeState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .swipeable(
                state = swipeState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            ), backgroundColor = viewModel.backgroundTaskItem(
            isDelete = item.isAccept,
            first = colors.primaryBackground,
            second = colors.secondaryBackground
        ), shape = MaterialTheme.shapes.small.copy(CornerSize(100))
    ) {
        TodoListItemView(
            swipeState = swipeState,
            viewModel = viewModel,
            item = item,
            navController = navController
        )
    }
}

@Composable
fun TodoListItemView(
    swipeState: SwipeableState<Int>,
    viewModel: MainViewModel,
    item: Todo,
    navController: NavController
) {
    val deleteIcon = R.drawable.ic_baseline_delete_outline_24
    val acceptIcon = R.drawable.ic_baseline_check_circle_outline_24

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.offset { IntOffset(swipeState.offset.value.roundToInt(), 0) },
    ) {
        IconButton(onClick = {
            viewModel.deletePress(item)
        }, modifier = Modifier.offset((-32).dp, 0.dp)) {

            Icon(
                painter = painterResource(id = deleteIcon),
                tint = colors.primaryButton,
                contentDescription = "delete"
            )
        }

        Text(
            text = checkNotNull(value = item.text),
            modifier = Modifier
                .weight(1F)
                .clickable(true) {
                    navController.navigate(NavigationTree.Additional.name)
                    item.isChecked
                },
            color = colors.primaryText,
            fontSize = 16.sp,
            style = LocalTextStyle.current.copy(
                textDecoration = viewModel.taskTextStyle(isDelete = item.isAccept)
            )
        )

        IconButton(
            onClick = {
                viewModel.acceptPress(id = item.id, text = item.text)
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.1F),
            enabled = viewModel.acceptEnabled(item.isAccept)
        ) {

            Icon(
                painter = painterResource(id = acceptIcon), tint = viewModel.drawableColorAccept(
                    isAccept = item.isAccept,
                    first = colors.primaryButton,
                    second = colors.disabledButton
                ), contentDescription = "accept"
            )
        }

    }
}
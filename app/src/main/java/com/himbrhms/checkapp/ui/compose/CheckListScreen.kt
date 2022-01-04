package com.himbrhms.checkapp.ui.compose

import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.common.events.CheckListEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.ui.CheckListViewModel
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.flow.collect

@Composable
fun CheckListScreen(
    onNavigate: (UiEvent.NavigateEvent) -> Unit,
    viewModel: CheckListViewModel = hiltViewModel()
) {
    val logger = Logger("CheckListScreen")
    val checkList = viewModel.toDoList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        logger.info("LaunchedEffect")
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onCheckListEvent(CheckListEvent.OnDeleteUndo)
                    }
                }
                is UiEvent.NavigateEvent -> {
                    onNavigate(event)
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onCheckListEvent(CheckListEvent.OnAddItem)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(2.dp)
        ) {
            items(checkList.value) { checkListItem ->
                CheckListItem(
                    item = checkListItem,
                    onEvent = viewModel::onCheckListEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onCheckListEvent(CheckListEvent.OnClickItem(checkListItem))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

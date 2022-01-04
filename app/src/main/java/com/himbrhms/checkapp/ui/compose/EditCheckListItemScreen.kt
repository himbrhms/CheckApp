package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.common.events.EditCheckListItemEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.ui.EditCheckListItemViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun EditCheckListItemScreen(
    onPopBackStack: () -> Unit,
    viewModel: EditCheckListItemViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackstack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    EditCheckListItemScaffold(scaffoldState, viewModel.title, viewModel.description) { event ->
        viewModel.onEvent(event)
    }
}

@Composable
fun EditCheckListItemScaffold(
    scaffoldState: ScaffoldState,
    title: String,
    description: String,
    onEventCallback: (EditCheckListItemEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEventCallback(EditCheckListItemEvent.OnSaveItem)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = title,
                onValueChange = {
                    onEventCallback(EditCheckListItemEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = description,
                onValueChange = {
                    onEventCallback(EditCheckListItemEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Notes")
                },
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                singleLine = false,
            )
        }
    }
}

@Preview
@Composable
fun EditCheckListItemScreenPreview() {
    EditCheckListItemScaffold(
        scaffoldState = ScaffoldState(
            drawerState = DrawerState(DrawerValue.Open),
            snackbarHostState = SnackbarHostState()
        ),
        title = "Preview",
        description = "PreviewDescription"
    ) {
        EditCheckListItemEvent.OnSaveItem
    }
}

package com.himbrhms.checkapp.ui.compose

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.common.events.EditNoteEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.ui.EditNoteViewModel
import com.himbrhms.checkapp.ui.util.DesertSand
import com.himbrhms.checkapp.ui.util.LightDesertSand
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@Composable
fun EditItemScreen(
    onPopBackStack: () -> Unit,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackstack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )
                }
                else -> Unit
            }
        }
    }
    EditItemScaffold(scaffoldState, viewModel.title, viewModel.description) { event ->
        viewModel.onEvent(event)
    }
}

@ExperimentalMaterialApi
@Composable
fun EditItemScaffold(
    scaffoldState: ScaffoldState,
    title: String,
    description: String,
    onEvent: (EditNoteEvent) -> Unit
) {
    SaveOnBackPressed(onEvent)
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            Column() {
                EditFloatingActionButton(
                    onEvent = { onEvent(EditNoteEvent.OnColorizeBottomSheet) },
                    drawableRes = R.drawable.ic_outline_color_lens_24,
                    description = "Colorize"
                )
                Spacer(modifier = Modifier.height(8.dp))
                EditFloatingActionButton(
                    onEvent = { onEvent(EditNoteEvent.OnColorizeBottomSheet) },
                    drawableRes = R.drawable.ic_outline_add_photo_alternate_24,
                    description = "Add Picture"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = title,
                onValueChange = { onEvent(EditNoteEvent.OnTitleChange(it)) },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightDesertSand
                )
            )
            TextField(
                value = description,
                onValueChange = { onEvent(EditNoteEvent.OnDescriptionChange(it)) },
                placeholder = { Text(text = "Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightDesertSand
                )
            )
        }
    }
}

@Composable
private fun EditFloatingActionButton(
    onEvent: (EditNoteEvent) -> Unit,
    @DrawableRes drawableRes: Int,
    description: String
) {
    FloatingActionButton(
        onClick = { onEvent },
        modifier = Modifier.scale(0.8f),
        backgroundColor = Color.DesertSand,
    ) {
        Icon(
            painterResource(id = drawableRes),
            contentDescription = description,
            modifier = Modifier.scale(1.4f)
        )
    }
}

@Composable
private fun SaveOnBackPressed(onEventCallback: (EditNoteEvent) -> Unit) {
    BackHandler {
        onEventCallback(EditNoteEvent.OnSaveItem)
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun EditItemScaffoldPreview() {
    EditItemScaffold(
        scaffoldState = ScaffoldState(
            drawerState = DrawerState(DrawerValue.Open),
            snackbarHostState = SnackbarHostState()
        ),
        title = "Preview",
        description = "PreviewDescription"
    ) {
        EditNoteEvent.OnSaveItem
    }
}

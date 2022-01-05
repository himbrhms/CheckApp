package com.himbrhms.checkapp.ui.compose

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.model.events.EditNoteEvent
import com.himbrhms.checkapp.model.events.UiEvent.ColorizeBottomSheetEvent
import com.himbrhms.checkapp.model.events.UiEvent.PopBackstackEvent
import com.himbrhms.checkapp.model.events.UiEvent.ShowToastEvent
import com.himbrhms.checkapp.model.EditNoteViewModel
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.ui.theme.LightDesertSand
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@Composable
fun EditItemScreen(
    onPopBackStack: () -> Unit,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val logger = Logger("EditItemScreen")
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PopBackstackEvent -> onPopBackStack()
                is ShowToastEvent -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is ColorizeBottomSheetEvent -> {
                    logger.info("ColorizeBottomSheetEvent: isVisible=${bottomSheetState?.isVisible}")
                    if (bottomSheetState?.isVisible == true) {
                        bottomSheetState?.hide()
                    } else if (bottomSheetState?.isVisible == false) {
                        bottomSheetState?.show()
                    }
                }
                else -> Unit
            }
        }
    }
    EditItemScaffold(scaffoldState, viewModel.title, viewModel.description) { event ->
        viewModel.onEditNoteEvent(event)
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
            .padding(10.dp),
        floatingActionButton = {
            Column() {
                FloatingActionButton(
                    onClick = { onEvent(EditNoteEvent.OnColorizeBottomSheet) },
                    modifier = Modifier.scale(0.8f),
                    backgroundColor = Color.DesertSand,
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_outline_color_lens_24),
                        contentDescription = "Colorize",
                        modifier = Modifier.scale(1.4f)
                    )
                }
                FloatingActionButton(
                    onClick = { onEvent(EditNoteEvent.OnAddImage) },
                    modifier = Modifier.scale(0.8f),
                    backgroundColor = Color.DesertSand,
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_outline_add_photo_alternate_24),
                        contentDescription = "Add Picturee",
                        modifier = Modifier.scale(1.4f)
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 10.dp)
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
        ColorizeBottomSheet()
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

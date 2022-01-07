package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.model.events.UiEvent
import com.himbrhms.checkapp.model.NoteListViewModel
import com.himbrhms.checkapp.model.events.ModelEvent
import com.himbrhms.checkapp.model.events.ModelEvent.OnAddNote
import com.himbrhms.checkapp.model.events.ModelEvent.OnClickNote
import com.himbrhms.checkapp.model.events.ModelEvent.OnDeleteNoteUndo
import com.himbrhms.checkapp.ui.editnotescreen.colorBottomSheetState
import com.himbrhms.checkapp.ui.theme.DesertSand
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NoteListScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteList = viewModel.noteList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.OnShowSnackBar -> {
                    viewModel.viewModelScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(OnDeleteNoteUndo)
                        }
                    }
                }
                is UiEvent.OnNavigate -> {
                    onNavigate(event)
                }
                is UiEvent.OnSelectNote -> {
                }
                is UiEvent.OnShowHideNoteListBottomSheet -> {
                    if (noteListBottomSheetState?.isVisible == true) {
                        noteListBottomSheetState?.hide()
                    } else if (noteListBottomSheetState?.isVisible == false) {
                        noteListBottomSheetState?.show()
                    }
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(OnAddNote) },
                modifier = Modifier.scale(0.8f),
                backgroundColor = Color.DesertSand,
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_baseline_post_add_24),
                    contentDescription = "Add Note",
                    modifier = Modifier.scale(1.4f)
                )
            }
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            cells = GridCells.Fixed(2)
        ) {
            items(noteList.value) { note ->
                NoteItem(
                    note = note,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { viewModel.onEvent(OnClickNote(note)) },
                            onLongClick = {
                                viewModel.onEvent(
                                    ModelEvent.OnToggleSelectedNote(note)
                                )
                            }
                        )
                        .padding(16.dp)
                )
            }
        }
    }
}


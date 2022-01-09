package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.viewmodel.events.UiEvent
import com.himbrhms.checkapp.viewmodel.NoteListViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.OnAddNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.OnClickNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.OnDeleteNotesUndo
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.OnLongClickNote
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NoteListScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteList = viewModel.noteList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val selectedNotes = remember { mutableStateListOf<Int?>() }
    val buttonsVisible = remember { mutableStateOf(false) }
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
                            viewModel.onEvent(OnDeleteNotesUndo)
                        }
                    }
                }
                is UiEvent.OnNavigate -> {
                    onNavigate(event)
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
            Column {
                AnimatedVisibility(
                    visible = buttonsVisible.value,
                    enter = fadeIn()
                ) {
                    FloatingActionButton(
                        onClick = {
                            selectedNotes.clear()
                            viewModel.onEvent(ViewModelEvent.OnShareNotes)
                        },
                        modifier = Modifier.scale(0.8f),
                        backgroundColor = Color.DesertSand,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_baseline_share_24),
                            contentDescription = "Share",
                            modifier = Modifier.scale(1.4f)
                        )
                    }
                }
                FloatingActionButton(
                    onClick = {
                        selectedNotes.clear()
                        viewModel.onEvent(ViewModelEvent.OnCopyNotes)
                    },
                    modifier = Modifier.scale(0.8f),
                    backgroundColor = Color.DesertSand,
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_content_copy_24),
                        contentDescription = "Copy",
                        modifier = Modifier.scale(1.4f)
                    )
                }
                FloatingActionButton(
                    onClick = {
                        selectedNotes.clear()
                        viewModel.onEvent(ViewModelEvent.OnDeleteNotes)
                    },
                    modifier = Modifier.scale(0.8f),
                    backgroundColor = Color.DesertSand,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.scale(1.4f)
                    )
                }
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
                    borderStroke = if (selectedNotes.contains(note.id)) {
                        BorderStroke(width = 4.dp, color = Color.Blue)
                    } else {
                        BorderStroke(width = 2.dp, color = Color.LightGray)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { viewModel.onEvent(OnClickNote(note)) },
                            onLongClick = {
                                if (selectedNotes.contains(note.id)) {
                                    selectedNotes.remove(note.id)
                                } else {
                                    selectedNotes.add(note.id)
                                }
                                viewModel.onEvent(OnLongClickNote(note))
                            }
                        )
                        .padding(16.dp)
                )
            }
        }
    }
}

package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.animation.*
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
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.AddNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ClickOnNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.UndoDeletedNotes
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.LongClickOnNote
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.ui.util.DesertFAB
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NoteListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteList = viewModel.noteList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    val selectedNotes = remember { mutableStateListOf<Int?>() }
    val buttonsVisible = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    viewModel.viewModelScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onEvent(UndoDeletedNotes)
                        }
                    }
                }
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowHideNoteListBottomSheet -> {
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
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    DesertFAB(
                        onClick = {
                            selectedNotes.clear()
                            viewModel.onEvent(ViewModelEvent.ShareSelectedNotes)
                        },
                        contentDescription = "Share",
                        drawableRes = R.drawable.ic_baseline_share_24
                    )
                }
                AnimatedVisibility(
                    visible = buttonsVisible.value,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    DesertFAB(
                        onClick = {
                            selectedNotes.clear()
                            viewModel.onEvent(ViewModelEvent.CopySelectedNotes)
                        },
                        contentDescription = "Copy",
                        drawableRes = R.drawable.ic_baseline_content_copy_24
                    )
                }
                AnimatedVisibility(
                    visible = buttonsVisible.value,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    DesertFAB(
                        onClick = {
                            selectedNotes.clear()
                            viewModel.onEvent(ViewModelEvent.DeleteSelectedNotes)
                        },
                        contentDescription = "Delete",
                        drawableRes = R.drawable.ic_outline_delete_24
                    )
                }
                DesertFAB(
                    onClick = { viewModel.onEvent(AddNote) },
                    contentDescription = "Add Note",
                    drawableRes = R.drawable.ic_baseline_post_add_24
                )
            }
        }
    ) {
        LazyVerticalGrid( // TODO: this lazy vertical grid is not staggered...
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
                    onClick = { viewModel.onEvent(ClickOnNote(note)) },
                    onLongClick = {
                        selectedNotes.apply {
                            if (contains(note.id)) remove(note.id) else add(note.id)
                        }
                        buttonsVisible.value = !selectedNotes.isEmpty()
                        viewModel.onEvent(LongClickOnNote(note))
                    }
                )
            }
        }
    }
}

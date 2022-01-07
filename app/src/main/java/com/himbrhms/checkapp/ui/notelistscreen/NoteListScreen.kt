package com.himbrhms.checkapp.ui.compose

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
import com.himbrhms.checkapp.model.events.NoteListEvent
import com.himbrhms.checkapp.model.events.UiEvent
import com.himbrhms.checkapp.model.NoteListViewModel
import com.himbrhms.checkapp.ui.theme.DesertSand
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun NoteListScreen(
    onNavigate: (UiEvent.NavigateEvent) -> Unit,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteList = viewModel.noteList.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBarEvent -> {
                    viewModel.viewModelScope.launch {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        val result = scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onNoteListEvent(NoteListEvent.OnDeleteNoteUndo)
                        }
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
            FloatingActionButton(
                onClick = { viewModel.onNoteListEvent(NoteListEvent.OnAddNote) },
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
            items(noteList.value) { noteItem ->
                NoteItem(
                    note = noteItem,
                    onEvent = viewModel::onNoteListEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = { viewModel.onNoteListEvent(NoteListEvent.OnClickNote(noteItem)) },
                            onLongClick = {  }
                        )
                        .padding(16.dp)
                )
            }
        }
    }
}


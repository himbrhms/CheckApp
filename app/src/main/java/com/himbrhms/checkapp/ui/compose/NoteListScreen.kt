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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.common.events.NoteListEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.ui.NoteListViewModel
import com.himbrhms.checkapp.ui.util.DesertSand
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.flow.collect

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
                is UiEvent.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onNoteListEvent(NoteListEvent.OnDeleteNoteUndo)
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            items(noteList.value) { noteItem ->
                NoteItem(
                    item = noteItem,
                    onEvent = viewModel::onNoteListEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onNoteListEvent(NoteListEvent.OnClickNote(noteItem))
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

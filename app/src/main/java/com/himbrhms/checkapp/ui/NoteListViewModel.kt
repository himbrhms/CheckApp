package com.himbrhms.checkapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.Routes
import com.himbrhms.checkapp.common.events.NoteListEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.common.events.NoteListEvent.OnAddNote
import com.himbrhms.checkapp.common.events.NoteListEvent.OnChangeChecked
import com.himbrhms.checkapp.common.events.NoteListEvent.OnClickNote
import com.himbrhms.checkapp.common.events.NoteListEvent.OnDeleteNote
import com.himbrhms.checkapp.common.events.NoteListEvent.OnDeleteNoteUndo
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repo: NoteListRepo
) : ViewModel() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    val noteList = repo.getNoteList()

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var lastDeletedNote: Note? = null

    fun onNoteListEvent(event: NoteListEvent) {
        when (event) {
            is OnAddNote -> {
                logger.info("onNoteListEvent(${event::class.simpleName})")
                sendUiEventAsync(
                    UiEvent.NavigateEvent(Routes.EDIT_NOTE_SCREEN)
                )
            }
            is OnClickNote -> {
                logger.info("onNoteListEvent(${event::class.simpleName})")
                sendUiEventAsync(UiEvent.NavigateEvent(Routes.EDIT_NOTE_SCREEN  + "?itemId=${event.item.id}"))
            }
            is OnChangeChecked -> {
                logger.info("onNoteListEvent(${event::class.simpleName})")
                viewModelScope.launch {
                    repo.insertNote(event.item.copy(isChecked = event.newIsChecked))
                }
            }
            is OnDeleteNote -> {
                logger.info("onNoteListEvent(${event::class.simpleName})")
                viewModelScope.launch {
                    lastDeletedNote = event.item
                    repo.deleteNote(event.item)
                }
                sendUiEventAsync(
                    UiEvent.ShowSnackBar(
                        message = "Note \"${event.item.title}\" deleted",
                        action = "UNDO"
                    )
                )
            }
            is OnDeleteNoteUndo -> {
                logger.info("onNoteListEvent(${event::class.simpleName})")
                lastDeletedNote?.let { toDo ->
                    viewModelScope.launch { repo.insertNote(toDo) }
                }
            }
        }
    }

    private fun sendUiEventAsync(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}

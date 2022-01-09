package com.himbrhms.checkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.Routes.EDIT_NOTE_SCREEN
import com.himbrhms.checkapp.viewmodel.events.UiEvent.OnNavigate
import com.himbrhms.checkapp.viewmodel.events.UiEvent.OnShowSnackBar
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.data.NoteCache
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.UiEvent
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
    private val repo: NoteListRepo,
    private val noteCache: NoteCache
) : ViewModel() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    val noteList = repo.getNoteList()

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    fun onEvent(event: ViewModelEvent) {
        logger.info("onEvent(event=${event.name})")
        when (event) {
            is ViewModelEvent.OnAddNote -> sendUiEvent(OnNavigate(EDIT_NOTE_SCREEN))
            is ViewModelEvent.OnClickNote -> {
                sendUiEvent(OnNavigate(EDIT_NOTE_SCREEN + "?itemId=${event.note.id}"))
            }
            is ViewModelEvent.OnLongClickNote -> onNoteSelected(event)
            is ViewModelEvent.OnDeleteNotes -> {
                if (noteCache.isEmpty()) return
                deleteNotes()
                sendUiEvent(OnShowSnackBar("Note deleted", action = "UNDO"))
            }
            is ViewModelEvent.OnDeleteNotesUndo -> undoDeleteNotes()
            else -> logger.warn("onEvent: event=${event.name} unhandled")
        }
    }

    private var deletedCalled = false

    private fun onNoteSelected(event: ViewModelEvent.OnLongClickNote) {
        val selectedNote = event.note
        if (deletedCalled) {
            deletedCalled = false
            noteCache.clear()
        }
        if (noteCache.contains(selectedNote.id!!)) {
            noteCache.remove(selectedNote.id)
        } else {
            noteCache.insert(selectedNote.id, selectedNote)
        }
    }

    private fun deleteNotes() {
        deletedCalled = true
        viewModelScope.launch {
            val notesToDelete = noteCache.getAllValues()
            notesToDelete.values.forEach { noteToDelete ->
                repo.deleteNote(noteToDelete)
            }
        }
    }

    private fun undoDeleteNotes() {
        val deletedNotes = noteCache.getAllValues()
        logger.info("OnDeleteNoteUndo: ${deletedNotes.values.joinToString()}")
        viewModelScope.launch {
            deletedNotes.values.forEach { note ->
                repo.insertNote(note)
            }
            noteCache.clear()
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}

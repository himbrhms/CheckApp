package com.himbrhms.checkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.Routes.EDIT_NOTE_SCREEN
import com.himbrhms.checkapp.viewmodel.events.UiEvent.Navigate
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowSnackBar
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.model.NoteActionManager
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
    private val actionManager: NoteActionManager,
    repo: NoteListRepo
) : ViewModel(), OnNoteListScreen {

    companion object {
        private val logger = Logger(this::class.className)

        // TODO: How to show SnackBar on another Screen/ViewModel?
        //  changing coroutine scope did not work
        var onNoteListScreen: OnNoteListScreen? = null // a little bit dirty...
    }

    val noteList = repo.getNoteList()

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    init {
        onNoteListScreen = this
    }

    fun onEvent(event: ViewModelEvent) {
        logger.info("onEvent(event=${event.name})")
        when (event) {
            is ViewModelEvent.AddNote -> send(Navigate(EDIT_NOTE_SCREEN))
            is ViewModelEvent.ClickOnNote -> {
                send(Navigate(EDIT_NOTE_SCREEN + "?itemId=${event.note.id}"))
            }
            is ViewModelEvent.LongClickOnNote -> {
                viewModelScope.launch { actionManager.onSelectNote(event.note) }
            }
            is ViewModelEvent.DeleteSelectedNotes -> {
                viewModelScope.launch { actionManager.onDeleteSelectedNotes() }
                showSnackBar(message = "Note deleted", action = "UNDO")
            }
            is ViewModelEvent.UndoDeletedNotes -> {
                viewModelScope.launch { actionManager.onUndoDeletedNotes() }
            }
            is ViewModelEvent.CopySelectedNotes -> {
                viewModelScope.launch { actionManager.onCopySelectedNotes() }
            }
            is ViewModelEvent.ShareSelectedNotes -> actionManager.onShareSelectedNotes()
            else -> logger.warn("onEvent: event=${event.name} unhandled")
        }
    }

    override fun showSnackBar(message: String, action: String?) {
        send(ShowSnackBar("Notes deleted", action = "UNDO"))
    }

    private fun send(uiEvent: UiEvent) = viewModelScope.launch { _uiEvent.send(uiEvent) }
}

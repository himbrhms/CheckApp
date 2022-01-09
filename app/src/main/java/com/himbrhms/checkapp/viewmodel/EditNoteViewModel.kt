package com.himbrhms.checkapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.viewmodel.events.UiEvent
import com.himbrhms.checkapp.viewmodel.events.UiEvent.PopBackStack
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowHideColorPickerSheet
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowToast
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.model.NoteActionManager
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ColorChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.DeleteSelectedNotes
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.DescriptionChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ToggleColorPickerBottomSheet
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.SaveNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleChange
import com.himbrhms.checkapp.ui.theme.ColorL
import com.himbrhms.checkapp.ui.theme.LightDesertSand
import com.himbrhms.checkapp.ui.theme.longValue
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repo: NoteListRepo,
    private val actionManager: NoteActionManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    var repoNote by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var backgroundColor by mutableStateOf(Color.LightDesertSand)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val itemId = savedStateHandle.get<Int>("itemId")!!
        if (itemId != -1) {
            viewModelScope.launch {
                repo.getNoteById(itemId)?.let { note ->
                    title = note.title
                    description = note.notes ?: ""
                    this@EditNoteViewModel.repoNote = note
                    backgroundColor = ColorL(note.backgroundColorValue)
                }
            }
        }
    }

    fun onEvent(event: ViewModelEvent) {
        logger.debug("onEvent(${event.name})")
        when (event) {
            is TitleChange -> {
                title = event.title
            }
            is DescriptionChange -> {
                description = event.description
            }
            is DeleteSelectedNotes -> {
                send(PopBackStack) // back to NoteListScreen
                if (isEmptyNote()) {
                    send(ShowToast(message = "Empty Note dismissed"))
                    return
                }
                val noteToDelete = Note(repoNote?.id, title, description, backgroundColor.longValue)
                viewModelScope.launch { actionManager.onDeleteEditedNote(noteToDelete) }
                NoteListViewModel.onNoteListScreen?.showSnackBar(
                    message = "Note deleted",
                    action = "UNDO"
                )
            }
            is SaveNote -> {
                viewModelScope.launch {
                    if (!isEmptyNote()) {
                        repo.insertNote(
                            Note(
                                title = title,
                                notes = description,
                                id = repoNote?.id,
                                backgroundColorValue = backgroundColor.longValue
                            )
                        )
                    } else {
                        send(ShowToast(message = "Empty Note dismissed"))
                    }
                    send(PopBackStack)
                }
            }
            is ToggleColorPickerBottomSheet -> {
                send(ShowHideColorPickerSheet)
            }
            is ColorChange -> {
                backgroundColor = event.color
                send(ShowHideColorPickerSheet)
            }
            else -> Unit
        }
    }

    private fun isEmptyNote(): Boolean {
        return this.title.isBlank() && description.isBlank()
    }

    private fun send(event: UiEvent) = viewModelScope.launch { _uiEvent.send(event) }
}

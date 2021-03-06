package com.himbrhms.checkapp.viewmodel

import androidx.compose.runtime.State
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
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowHideGroupingBottomSheet
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowToast
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.model.NoteActionManager
import com.himbrhms.checkapp.ui.editnotescreen.NoteTextFieldState
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ColorChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.DeleteSelectedNotes
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ContentChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ColorPickerBottomSheet
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.SaveNote
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleChange
import com.himbrhms.checkapp.ui.theme.colorFromLong
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

    // TODO: just note id required
    var repoNote by mutableStateOf<Note?>(null)
        private set

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Title"))
    val noteTitle: State<NoteTextFieldState> = this._noteTitle

    private var _noteContent = mutableStateOf(NoteTextFieldState(hint = "Notes"))
    val noteContent: State<NoteTextFieldState> = this._noteContent

    // TODO: Implement Note grouping
    private val _noteGroup = mutableStateOf(NoteTextFieldState(hint = "Group"))
    val noteGroup: State<NoteTextFieldState> = this._noteGroup

    // TODO: refactor
    var backgroundColor by mutableStateOf(Color.LightDesertSand)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val itemId = savedStateHandle.get<Int>("itemId")!!
        if (itemId != -1) {
            viewModelScope.launch {
                repo.getNoteById(itemId)?.let { note ->
                    _noteGroup.value = noteGroup.value.copy(
                        text = note.title,
                        isHintVisible = false
                    )
                    _noteContent.value = noteContent.value.copy(
                        text = note.content,
                        isHintVisible = false
                    )
                    this@EditNoteViewModel.repoNote = note
                    backgroundColor = colorFromLong(note.colorValue)
                }
            }
        }
    }

    fun onEvent(event: ViewModelEvent) {
        logger.debug("onEvent(${event.name})")
        when (event) {
            is TitleChange -> {
                _noteGroup.value = noteGroup.value.copy(text = event.title)
            }
            is ViewModelEvent.TitleFocusChange -> {
                _noteGroup.value = noteGroup.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteGroup.value.text.isBlank()
                )
            }
            is ContentChange -> {
                _noteContent.value = noteContent.value.copy(text = event.content)
            }
            is ViewModelEvent.ContentFocusChange -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is DeleteSelectedNotes -> {
                send(PopBackStack) // back to NoteListScreen
                if (isEmptyNote()) {
                    send(ShowToast(message = "Empty Note dismissed"))
                    return
                }
                val noteToDelete = Note(
                    repoNote?.id,
                    _noteGroup.value.text,
                    _noteContent.value.text,
                    _noteGroup.value.text,
                    backgroundColor.longValue
                )
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
                                title = _noteGroup.value.text,
                                content = _noteContent.value.text,
                                id = repoNote?.id,
                                colorValue = backgroundColor.longValue
                            )
                        )
                    } else {
                        send(ShowToast(message = "Empty Note dismissed"))
                    }
                    send(PopBackStack)
                }
            }
            is ColorPickerBottomSheet -> {
                send(ShowHideColorPickerSheet)
            }
            is ViewModelEvent.ToggleGroupingBottomSheet -> {
                send(ShowHideGroupingBottomSheet)
            }
            is ColorChange -> {
                backgroundColor = event.color
                send(ShowHideColorPickerSheet)
            }
            else -> Unit
        }
    }

    private fun isEmptyNote(): Boolean {
        return this._noteGroup.value.text.isBlank() && this._noteContent.value.text.isBlank()
    }

    private fun send(event: UiEvent) = viewModelScope.launch { _uiEvent.send(event) }
}

package com.himbrhms.checkapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.events.EditNoteEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repo: NoteListRepo,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var item by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var color by mutableStateOf(0xFFFFFFL)
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val itemId = savedStateHandle.get<Int>("itemId")!!
        if(itemId != -1) {
            viewModelScope.launch {
                repo.getNoteById(itemId)?.let { item ->
                    title = item.title
                    description = item.notes ?: ""
                    this@EditNoteViewModel.item = item
                }
            }
        }
    }

    fun onEvent(event: EditNoteEvent) {
        when(event) {
            is EditNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is EditNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
            is EditNoteEvent.OnSaveItem -> {
                viewModelScope.launch {
                    if(!isEmptyItem()) {
                        repo.insertNote(
                            Note(
                                title = title,
                                notes = description,
                                isChecked = item?.isChecked ?: false,
                                id = item?.id,
                                backColorValue = color
                            )
                        )
                    } else {
                        sendUiEventAsync(UiEvent.ShowSnackBar(message = "Empty Note dismissed"))
                    }
                    sendUiEventAsync(UiEvent.PopBackstack)
                }
            }
        }
    }

    private fun isEmptyItem(): Boolean {
        return title.isBlank() && description.isBlank()
    }

    private fun sendUiEventAsync(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

package com.himbrhms.checkapp.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.model.events.EditNoteEvent
import com.himbrhms.checkapp.model.events.UiEvent
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteListRepo
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    var item by mutableStateOf<Note?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var color by mutableStateOf(Color.White)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val itemId = savedStateHandle.get<Int>("itemId")!!
        if (itemId != -1) {
            viewModelScope.launch {
                repo.getNoteById(itemId)?.let { item ->
                    title = item.title
                    description = item.notes ?: ""
                    this@EditNoteViewModel.item = item
                }
            }
        }
    }

    fun onEditNoteEvent(event: EditNoteEvent) {
        logger.debug("onEvent(${event.name})")
        when (event) {
            is EditNoteEvent.OnTitleChange -> {
                title = event.title
            }
            is EditNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
            is EditNoteEvent.OnSaveItem -> {
                viewModelScope.launch {
                    if (!isEmptyItem()) {
                        repo.insertNote(
                            Note(
                                title = title,
                                notes = description,
                                isChecked = item?.isChecked ?: false,
                                id = item?.id,
                                backgroundColorValue = color.value.toLong()
                            )
                        )
                    } else {
                        sendAsyncUiEvent(UiEvent.ShowToastEvent(message = "Empty Note dismissed"))
                    }
                    sendAsyncUiEvent(UiEvent.PopBackstackEvent)
                }
            }
            is EditNoteEvent.OnColorizeBottomSheet -> {
                sendAsyncUiEvent(UiEvent.ColorizeBottomSheetEvent)
            }
            is EditNoteEvent.OnColorChange -> {
                color = event.color
            }
        }
    }

    private fun isEmptyItem(): Boolean {
        return title.isBlank() && description.isBlank()
    }

    private fun sendAsyncUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

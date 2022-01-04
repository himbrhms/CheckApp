package com.himbrhms.checkapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.events.EditCheckListItemEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.data.CheckListItemData
import com.himbrhms.checkapp.data.CheckListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditCheckListItemViewModel @Inject constructor(
    private val repo: CheckListRepo,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var item by mutableStateOf<CheckListItemData?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val itemId = savedStateHandle.get<Int>("itemId")!!
        if(itemId != -1) {
            viewModelScope.launch {
                repo.getItem(itemId)?.let { item ->
                    title = item.title
                    description = item.description ?: ""
                    this@EditCheckListItemViewModel.item = item
                }
            }
        }
    }

    fun onEvent(event: EditCheckListItemEvent) {
        when(event) {
            is EditCheckListItemEvent.OnTitleChange -> {
                title = event.title
            }
            is EditCheckListItemEvent.OnDescriptionChange -> {
                description = event.description
            }
            is EditCheckListItemEvent.OnSaveItem -> {
                viewModelScope.launch {
                    if(title.isBlank()) {
                        sendUiEventAsync(UiEvent.ShowSnackBar(
                            message = "The title can't be empty"
                        ))
                        return@launch
                    }
                    repo.insertItem(
                        CheckListItemData(
                            title = title,
                            description = description,
                            isChecked = item?.isChecked ?: false,
                            id = item?.id
                        )
                    )
                    sendUiEventAsync(UiEvent.PopBackstack)
                }
            }
        }
    }

    private fun sendUiEventAsync(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

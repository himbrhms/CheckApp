package com.himbrhms.checkapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.common.RoutePaths
import com.himbrhms.checkapp.common.events.CheckListEvent
import com.himbrhms.checkapp.common.events.UiEvent
import com.himbrhms.checkapp.data.CheckListRepo
import com.himbrhms.checkapp.data.CheckListItem
import com.himbrhms.checkapp.common.events.CheckListEvent.OnAddItem
import com.himbrhms.checkapp.common.events.CheckListEvent.OnChangeChecked
import com.himbrhms.checkapp.common.events.CheckListEvent.OnClickItem
import com.himbrhms.checkapp.common.events.CheckListEvent.OnDeleteItem
import com.himbrhms.checkapp.common.events.CheckListEvent.OnDeleteUndo
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val repo: CheckListRepo
) : ViewModel() {

    companion object {
        private val logger = Logger(this::class.className)
    }

    val toDoList = repo.getToDoList()

    private val _uiEvent: Channel<UiEvent> = Channel<UiEvent>()
    val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    private var recentlyDeletedItem: CheckListItem? = null

    fun onCheckListEvent(event: CheckListEvent) {
        when (event) {
            is OnAddItem -> {
                logger.info("onCheckListEvent(${event::class.simpleName})")
                sendUiEventAsync(
                    UiEvent.Navigate(RoutePaths.ADD_EDIT_TODO + "?itemId=${event.item.id}")
                )
            }
            is OnClickItem -> {
                logger.info("onCheckListEvent(${event::class.simpleName})")
                sendUiEventAsync(UiEvent.Navigate(RoutePaths.ADD_EDIT_TODO))
            }
            is OnChangeChecked -> {
                logger.info("onCheckListEvent(${event::class.simpleName})")
                viewModelScope.launch {
                    repo.insertToDo(event.item.copy(isChecked = event.newIsChecked))
                }
            }
            is OnDeleteItem -> {
                logger.info("onCheckListEvent(${event::class.simpleName})")
                viewModelScope.launch {
                    recentlyDeletedItem = event.item
                    repo.deleteToDo(event.item)
                }
                sendUiEventAsync(
                    UiEvent.ShowSnackBar(
                        message = "Item ${event.item.title} deleted",
                        action = "undo"
                    )
                )
            }
            is OnDeleteUndo -> {
                logger.info("onCheckListEvent(${event::class.simpleName})")
                recentlyDeletedItem?.let { toDo ->
                    viewModelScope.launch { repo.insertToDo(toDo) }
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

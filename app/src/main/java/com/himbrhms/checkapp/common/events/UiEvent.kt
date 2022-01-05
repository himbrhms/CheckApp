package com.himbrhms.checkapp.common.events

sealed class UiEvent {
    object PopBackstack : UiEvent()
    data class NavigateEvent(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
}

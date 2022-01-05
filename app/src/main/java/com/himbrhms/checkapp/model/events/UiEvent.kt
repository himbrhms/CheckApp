package com.himbrhms.checkapp.model.events

import androidx.compose.ui.graphics.Color

sealed class UiEvent {
    object PopBackstackEvent : UiEvent()
    object ColorizeBottomSheetEvent: UiEvent()
    data class NavigateEvent(val route: String) : UiEvent()
    data class ShowSnackBarEvent(val message: String, val action: String? = null) : UiEvent()
    data class ShowToastEvent(val message: String) : UiEvent()
    data class ColorChangeEvent(val color: Color) : UiEvent()
}

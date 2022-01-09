package com.himbrhms.checkapp.viewmodel.events

sealed class UiEvent {
    object PopBackStack : UiEvent()
    object ShowHideColorPickerSheet: UiEvent()
    object ShowHideNoteListBottomSheet: UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String, val action: String? = null) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
}

package com.himbrhms.checkapp.viewmodel.events

sealed class UiEvent {
    object OnPopBackstack : UiEvent()
    object OnShowHideColorPickerSheet: UiEvent()
    object OnShowHideNoteListBottomSheet: UiEvent()
    data class OnNavigate(val route: String) : UiEvent()
    data class OnShowSnackBar(val message: String, val action: String? = null) : UiEvent()
    data class OnShowToast(val message: String) : UiEvent()
}
package com.himbrhms.checkapp.common.events

sealed class EditNoteEvent {
    data class OnTitleChange(val title: String): EditNoteEvent()
    data class OnDescriptionChange(val description: String): EditNoteEvent()
    object OnColorizeBottomSheet: EditNoteEvent()
    object OnSaveItem: EditNoteEvent()
    object OnOpenBottomSheet: EditNoteEvent()
}

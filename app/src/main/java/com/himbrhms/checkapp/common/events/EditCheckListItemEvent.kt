package com.himbrhms.checkapp.common.events

sealed class EditCheckListItemEvent {
    data class OnTitleChange(val title: String): EditCheckListItemEvent()
    data class OnDescriptionChange(val description: String): EditCheckListItemEvent()
    data class OnColorChange(val colorValue: Long): EditCheckListItemEvent()
    object OnSaveItem: EditCheckListItemEvent()
}

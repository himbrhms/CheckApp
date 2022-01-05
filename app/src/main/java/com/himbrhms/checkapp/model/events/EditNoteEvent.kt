package com.himbrhms.checkapp.model.events

import androidx.compose.ui.graphics.Color

sealed class EditNoteEvent {
    abstract val name: String
    data class OnTitleChange(val title: String): EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
    data class OnDescriptionChange(val description: String): EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
    object OnColorizeBottomSheet: EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
    object OnAddImage: EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
    object OnSaveItem: EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
    data class OnColorChange(val color: Color):  EditNoteEvent() {
        override val name: String = this::class.java.simpleName
    }
}

package com.himbrhms.checkapp.model.events

import androidx.compose.ui.graphics.Color
import com.himbrhms.checkapp.data.Note

sealed class ModelEvent {
    abstract val name: String

    object OnAddNote: ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnDeleteNote(val note: Note): ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnDeleteNoteUndo: ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnClickNote(val item: Note): ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnToggleSelectedNote(val item: Note): ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnTitleChange(val title: String): ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnDescriptionChange(val description: String): ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnToggleColorPickerBottomSheet: ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnAddImage: ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnSaveNote: ModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnColorChange(val color: Color):  ModelEvent() {
        override val name: String = this::class.java.simpleName
    }
}

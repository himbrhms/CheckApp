package com.himbrhms.checkapp.viewmodel.events

import androidx.compose.ui.graphics.Color
import com.himbrhms.checkapp.data.Note

sealed class ViewModelEvent {
    abstract val name: String

    object OnAddNote: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnDeleteNotes(val note: Note): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnDeleteNotesUndo: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnClickNote(val note: Note): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnSelectedNote(val note: Note): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnTitleChange(val title: String): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnDescriptionChange(val description: String): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnToggleColorPickerBottomSheet: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnAddImage: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnSaveNote: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class OnColorChange(val color: Color):  ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }
}

package com.himbrhms.checkapp.viewmodel.events

import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.himbrhms.checkapp.data.Note

sealed class ViewModelEvent {
    abstract val name: String

    object AddNote: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object DeleteSelectedNotes: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object CopySelectedNotes: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object ShareSelectedNotes: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object UndoDeletedNotes: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class ClickOnNote(val note: Note): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class LongClickOnNote(val note: Note): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class TitleChange(val title: String): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class TitleFocusChange(val focusState: FocusState): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class ContentFocusChange(val focusState: FocusState): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class ContentChange(val content: String): ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object ToggleColorPickerBottomSheet: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object OnAddImage: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    object SaveNote: ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }

    data class ColorChange(val color: Color):  ViewModelEvent() {
        override val name: String = this::class.java.simpleName
    }
}

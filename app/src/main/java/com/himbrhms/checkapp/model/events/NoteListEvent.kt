package com.himbrhms.checkapp.model.events

import com.himbrhms.checkapp.data.Note

sealed class NoteListEvent {
    object OnAddNote: NoteListEvent()
    data class OnClickNote(val item: Note): NoteListEvent()
    data class OnChangeChecked(val item: Note, val newIsChecked: Boolean): NoteListEvent()
    data class OnDeleteNote(val note: Note): NoteListEvent()
    object OnDeleteNoteUndo: NoteListEvent()
}

package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

interface NoteListRepo {

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id: Int): Note?

    fun getNoteList(): Flow<List<Note>>
}

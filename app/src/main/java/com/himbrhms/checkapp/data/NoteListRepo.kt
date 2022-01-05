package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

interface NoteListRepo {

    suspend fun insertNote(item: Note)

    suspend fun deleteNote(item: Note)

    suspend fun getNoteById(id: Int): Note?

    fun getNoteList(): Flow<List<Note>>
}

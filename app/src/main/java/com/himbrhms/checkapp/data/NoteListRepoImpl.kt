package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

class NoteListRepoImpl(private val toDoDao: NoteDao) : NoteListRepo {

    override suspend fun insertNote(note: Note) = toDoDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = toDoDao.deleteNote(note)

    override suspend fun getNoteById(id: Int): Note? = toDoDao.getNoteById(id)

    override fun getNoteList(): Flow<List<Note>> = toDoDao.getNoteList()
}

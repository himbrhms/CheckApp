package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

class NoteListRepoImpl(private val toDoDao: NoteDao) : NoteListRepo {

    override suspend fun insertNote(item: Note) = toDoDao.insertNote(item)

    override suspend fun deleteNote(item: Note) = toDoDao.deleteNote(item)

    override suspend fun getNoteById(id: Int): Note? = toDoDao.getNoteById(id)

    override fun getNoteList(): Flow<List<Note>> = toDoDao.getNoteList()
}

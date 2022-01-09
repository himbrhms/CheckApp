package com.himbrhms.checkapp.model

import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteCache
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import javax.inject.Inject

class NoteActionManager @Inject constructor(
    private val repo: NoteListRepo
) {

    companion object {
        private val logger = Logger(this::class.className)
    }

    private val noteCache = NoteCache()
    private var deletedCalled = false

    fun onSelectNote(selectedNote: Note) {
        if (deletedCalled) {
            deletedCalled = false
            noteCache.clear()
        }
        if (noteCache.contains(selectedNote.id!!)) {
            noteCache.remove(selectedNote.id)
        } else {
            noteCache.insert(selectedNote.id, selectedNote)
        }
    }

    suspend fun onCopySelectedNotes() {
        if (noteCache.isEmpty()) return
        val notesToCopy = noteCache.getAllValues()
        logger.info("onCopySelectedNotes: ${notesToCopy.joinToString()}")
        noteCache.getAllValues().forEach { note ->
            repo.insertNote(note.copy(id = null))
        }
        noteCache.clear()
    }

    suspend fun onDeleteSelectedNotes() {
        if (noteCache.isEmpty()) return
        deletedCalled = true
        val notesToDelete = noteCache.getCacheItems()
        logger.info("onDeleteSelectedNotes: ${notesToDelete.values.joinToString()}")
        notesToDelete.values.forEach { noteToDelete ->
            repo.deleteNote(noteToDelete)
        }
    }

    suspend fun onDeleteEditedNote(noteToDelete: Note) {
        repo.deleteNote(noteToDelete)
        noteCache.clear()
        val noteId = noteToDelete.id ?: Integer.MAX_VALUE
        noteCache.insert(noteId, noteToDelete)
    }

    suspend fun onUndoDeletedNotes() {
        val notesToUndoDelete = noteCache.getCacheItems()
        logger.info("onUndoDeletedNotes: ${notesToUndoDelete.values.joinToString()}")
        notesToUndoDelete.values.forEach { note ->
            repo.insertNote(note)
        }
        noteCache.clear()
    }
}
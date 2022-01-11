package com.himbrhms.checkapp.model

import android.content.Context
import com.himbrhms.checkapp.data.Note
import com.himbrhms.checkapp.data.NoteCache
import com.himbrhms.checkapp.data.NoteListRepo
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.util.className
import javax.inject.Inject
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import dagger.hilt.android.qualifiers.ActivityContext


class NoteActionManager @Inject constructor(
    private val repo: NoteListRepo,
    @ActivityContext
    private val context: Context
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
        val notesToUndoDelete = noteCache.getAllValues()
        logger.info("onUndoDeletedNotes: ${notesToUndoDelete.joinToString()}")
        notesToUndoDelete.forEach { note ->
            repo.insertNote(note)
        }
        noteCache.clear()
    }

    fun onShareSelectedNotes() {
        if (noteCache.isEmpty()) return
        val notesToShare = noteCache.getAllValues()
        logger.info("onShareSelectedNotes: ${notesToShare.joinToString()}")
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, notesToShare[0].title)
            putExtra(Intent.EXTRA_TEXT, "${notesToShare[0].title}\n${notesToShare[0].content}")
        }
        val chooserIntent = Intent.createChooser(shareIntent, "Share via").apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(chooserIntent)
        noteCache.clear()
    }
}

package com.himbrhms.checkapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [Note::class],
)
abstract class NoteListDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
}
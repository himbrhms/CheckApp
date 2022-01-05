package com.himbrhms.checkapp.common.di

import android.app.Application
import androidx.room.Room
import com.himbrhms.checkapp.data.NoteListDatabase
import com.himbrhms.checkapp.data.NoteListRepoImpl
import com.himbrhms.checkapp.data.NoteListRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteListDatabase(app: Application): NoteListDatabase =
        Room.databaseBuilder(app, NoteListDatabase::class.java, "NoteListDatabase").build()

    @Provides
    @Singleton
    fun provideNoteListRepo(noteListDatabase: NoteListDatabase): NoteListRepo =
        NoteListRepoImpl(noteListDatabase.noteDao)
}

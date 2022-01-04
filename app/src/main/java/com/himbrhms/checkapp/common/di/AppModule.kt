package com.himbrhms.checkapp.common.di

import android.app.Application
import androidx.room.Room
import com.himbrhms.checkapp.data.CheckListDatabase
import com.himbrhms.checkapp.data.CheckListRepoImpl
import com.himbrhms.checkapp.data.CheckListRepo
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
    fun provideToDoDatabase(app: Application): CheckListDatabase =
        Room.databaseBuilder(app, CheckListDatabase::class.java, "ToDoDB").build()

    @Provides
    @Singleton
    fun provideToDoRepoy(checkListDatabase: CheckListDatabase): CheckListRepo =
        CheckListRepoImpl(checkListDatabase.checkListDao)
}

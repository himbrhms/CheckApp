package com.himbrhms.checkapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [CheckListItemData::class],
)
abstract class CheckListDatabase : RoomDatabase() {

    abstract val checkListDao: CheckListDao
}
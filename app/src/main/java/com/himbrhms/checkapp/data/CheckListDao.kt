package com.himbrhms.checkapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CheckListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(item: CheckListItemData)

    @Delete
    suspend fun deleteToDo(item: CheckListItemData)

    @Query("SELECT * FROM checklistitem WHERE id = :id")
    suspend fun getToDo(id: Int): CheckListItemData?

    @Query("SELECT * FROM checklistitem")
    fun getAllToDo(): Flow<List<CheckListItemData>>
}

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
    suspend fun insertItem(item: CheckListItemData)

    @Delete
    suspend fun deleteItem(item: CheckListItemData)

    @Query("SELECT * FROM CheckListItemData WHERE id = :id")
    suspend fun getItem(id: Int): CheckListItemData?

    @Query("SELECT * FROM CheckListItemData")
    fun getCheckList(): Flow<List<CheckListItemData>>
}

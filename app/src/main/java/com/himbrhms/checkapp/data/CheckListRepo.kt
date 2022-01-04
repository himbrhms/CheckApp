package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

interface CheckListRepo {

    suspend fun insertItem(item: CheckListItemData)

    suspend fun deleteItem(item: CheckListItemData)

    suspend fun getItem(id: Int): CheckListItemData?

    fun getCheckList(): Flow<List<CheckListItemData>>
}
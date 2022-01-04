package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

interface CheckListRepo {

    suspend fun insertToDo(item: CheckListItemData)

    suspend fun deleteToDo(item: CheckListItemData)

    suspend fun getToDo(id: Int): CheckListItemData?

    fun getToDoList(): Flow<List<CheckListItemData>>
}
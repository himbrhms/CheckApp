package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

interface CheckListRepo {

    suspend fun insertToDo(item: CheckListItem)

    suspend fun deleteToDo(item: CheckListItem)

    suspend fun getToDo(id: Int): CheckListItem?

    fun getToDoList(): Flow<List<CheckListItem>>
}
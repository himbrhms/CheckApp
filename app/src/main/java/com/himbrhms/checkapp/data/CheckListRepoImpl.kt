package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

class CheckListRepoImpl(private val toDoDao: CheckListDao) : CheckListRepo {

    override suspend fun insertToDo(item: CheckListItemData) = toDoDao.insertToDo(item)

    override suspend fun deleteToDo(item: CheckListItemData) = toDoDao.deleteToDo(item)

    override suspend fun getToDo(id: Int): CheckListItemData? = toDoDao.getToDo(id)

    override fun getToDoList(): Flow<List<CheckListItemData>> = toDoDao.getAllToDo()
}

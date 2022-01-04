package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

class CheckListRepoImpl(private val toDoDao: CheckListDao) : CheckListRepo {

    override suspend fun insertToDo(item: CheckListItem) = toDoDao.insertToDo(item)

    override suspend fun deleteToDo(item: CheckListItem) = toDoDao.deleteToDo(item)

    override suspend fun getToDo(id: Int): CheckListItem? = toDoDao.getToDo(id)

    override fun getToDoList(): Flow<List<CheckListItem>> = toDoDao.getAllToDo()
}

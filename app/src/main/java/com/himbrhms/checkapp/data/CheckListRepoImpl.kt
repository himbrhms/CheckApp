package com.himbrhms.checkapp.data

import kotlinx.coroutines.flow.Flow

class CheckListRepoImpl(private val toDoDao: CheckListDao) : CheckListRepo {

    override suspend fun insertItem(item: CheckListItemData) = toDoDao.insertItem(item)

    override suspend fun deleteItem(item: CheckListItemData) = toDoDao.deleteItem(item)

    override suspend fun getItem(id: Int): CheckListItemData? = toDoDao.getItem(id)

    override fun getCheckList(): Flow<List<CheckListItemData>> = toDoDao.getCheckList()
}

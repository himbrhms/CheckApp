package com.himbrhms.checkapp.common.events

import com.himbrhms.checkapp.data.CheckListItemData

sealed class CheckListEvent {
    data class OnAddItem(val item: CheckListItemData): CheckListEvent()
    data class OnClickItem(val item: CheckListItemData): CheckListEvent()
    data class OnChangeChecked(val item: CheckListItemData, val newIsChecked: Boolean): CheckListEvent()
    data class OnDeleteItem(val item: CheckListItemData): CheckListEvent()
    object OnDeleteUndo: CheckListEvent()
}

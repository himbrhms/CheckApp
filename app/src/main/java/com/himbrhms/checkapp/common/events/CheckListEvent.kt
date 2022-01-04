package com.himbrhms.checkapp.common.events

import com.himbrhms.checkapp.data.CheckListItem

sealed class CheckListEvent {
    data class OnAddItem(val item: CheckListItem): CheckListEvent()
    data class OnClickItem(val item: CheckListItem): CheckListEvent()
    data class OnChangeChecked(val item: CheckListItem, val newIsChecked: Boolean): CheckListEvent()
    data class OnDeleteItem(val item: CheckListItem): CheckListEvent()
    object OnDeleteUndo: CheckListEvent()
}

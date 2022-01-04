package com.himbrhms.checkapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CheckListItem(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String = "",
    val isChecked: Boolean
)

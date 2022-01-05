package com.himbrhms.checkapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val notes: String = "",
    val isChecked: Boolean,
    val backColorValue: Long = 0xFFFFFF
)

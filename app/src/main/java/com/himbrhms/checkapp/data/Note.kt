package com.himbrhms.checkapp.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.himbrhms.checkapp.ui.theme.longValue

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val notes: String = "",
    val isChecked: Boolean,
    val backgroundColorValue: Long = Color.White.longValue
)

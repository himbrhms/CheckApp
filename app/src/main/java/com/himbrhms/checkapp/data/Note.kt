package com.himbrhms.checkapp.data

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.himbrhms.checkapp.ui.theme.longValue

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String = "",
    val group: String = "",
    val colorValue: Long = Color.White.longValue
) {
    override fun toString(): String = "Note(id=$id, title=$title, colorValue=$colorValue)"
}

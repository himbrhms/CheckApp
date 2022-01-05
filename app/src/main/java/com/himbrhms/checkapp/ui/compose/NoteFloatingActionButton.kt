package com.himbrhms.checkapp.ui.compose

import androidx.annotation.DrawableRes
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.himbrhms.checkapp.common.events.EditNoteEvent
import com.himbrhms.checkapp.ui.util.DesertSand

@Composable
fun NoteFloatingActionButton(
    onEvent: (EditNoteEvent) -> Unit,
    @DrawableRes drawableRes: Int,
    description: String
) {
    FloatingActionButton(
        onClick = { onEvent },
        modifier = Modifier.scale(0.8f),
        backgroundColor = Color.DesertSand,
    ) {
        Icon(
            painterResource(id = drawableRes),
            contentDescription = description,
            modifier = Modifier.scale(1.4f)
        )
    }
}
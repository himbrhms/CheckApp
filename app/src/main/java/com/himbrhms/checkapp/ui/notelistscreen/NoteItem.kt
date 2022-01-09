package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.himbrhms.checkapp.data.Note
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.ui.theme.ColorL

@Composable
internal fun NoteItem(
    note: Note,
    borderStroke: BorderStroke = BorderStroke(width = 2.dp, color = Color.LightGray),
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(
                borderStroke,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(ColorL(note.backgroundColorValue))
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                note.notes.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ComposablePreview() {
    NoteItem(
        Note(
            id = 1,
            title = "Preview",
            notes = "Notes",
            backgroundColorValue = Color.White.value.toLong()
        )
    )
}

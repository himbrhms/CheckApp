package com.himbrhms.checkapp.ui.notelistscreen

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
    onEvent: (ViewModelEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val boxBorderWidth = if (note.isSelected) 4.dp else 2.dp
    val boxBorderColor = if (note.isSelected) Color.Blue else Color.LightGray
    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(
                width = boxBorderWidth,
                color = boxBorderColor,
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
            IconButton(onClick = {
                onEvent(ViewModelEvent.OnDeleteNotes(note))
            }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview
@Composable
private fun ComposablePreview() {
    NoteItem(
        note = Note(1, "Preview", "Desc", true, Color.White.value.toLong()),
        onEvent = { ViewModelEvent.OnAddNote },
    )
}

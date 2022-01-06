package com.himbrhms.checkapp.ui.compose

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
import com.himbrhms.checkapp.model.events.NoteListEvent
import com.himbrhms.checkapp.ui.theme.ColorL
import com.himbrhms.checkapp.util.Logger

@Composable
fun NoteItem(
    note: Note,
    onEvent: (NoteListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Logger("NoteItem").info("note=$note")
    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(10.dp))
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                note.notes.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
            }
            IconButton(onClick = {
                onEvent(NoteListEvent.OnDeleteNote(note))
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
fun ComposablePreview() {
    NoteItem(
        Note(
            id = 1,
            title = "Preview",
            notes = "PreviewDescription",
            true,
            backgroundColorValue = Color.White.value.toLong()
        ),
        {
            NoteListEvent.OnAddNote
        }
    )
}

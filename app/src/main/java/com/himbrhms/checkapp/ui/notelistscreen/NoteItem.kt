package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.foundation.*
import com.himbrhms.checkapp.data.Note
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himbrhms.checkapp.ui.theme.colorFromLong

@ExperimentalFoundationApi
@Composable
internal fun NoteItem(
    note: Note,
    borderStroke: BorderStroke = BorderStroke(width = 2.dp, color = Color.LightGray),
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(
                borderStroke,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(colorFromLong(note.colorValue))
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis
            )
        }
        /*Row(
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
                note.content.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
            }
        }*/
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun ComposablePreview() {
    NoteItem(
        Note(
            id = 1,
            title = "Preview",
            content = "Notes",
            colorValue = Color.White.value.toLong()
        ),
        onClick = {},
        onLongClick = {}
    )
}

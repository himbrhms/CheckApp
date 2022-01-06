package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.model.EditNoteViewModel
import com.himbrhms.checkapp.model.events.EditNoteEvent
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.ui.theme.LightDesertSand

@Composable
fun NotePaper(
    viewModel: EditNoteViewModel,
    onEvent: (EditNoteEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .shadow(elevation = 10.dp)
            .background(viewModel.backgroundColor)
    ) {
        TextField(
            value = viewModel.title,
            onValueChange = { onEvent(EditNoteEvent.OnTitleChange(it)) },
            placeholder = { Text(text = "Title", fontSize = 20.sp) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = viewModel.backgroundColor
            ),
        )
        TextField(
            value = viewModel.description,
            onValueChange = { onEvent(EditNoteEvent.OnDescriptionChange(it)) },
            placeholder = { Text(text = "Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f),
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = viewModel.backgroundColor
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp)
        ) {
            FloatingActionButton(
                onClick = { onEvent(EditNoteEvent.OnColorizeBottomSheet) },
                modifier = Modifier.scale(0.8f),
                backgroundColor = Color.DesertSand,
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_outline_color_lens_24),
                    contentDescription = "Colorize",
                    modifier = Modifier.scale(1.4f)
                )
            }
            Spacer(Modifier.weight(1f))
            FloatingActionButton(
                onClick = { onEvent(EditNoteEvent.OnAddImage) },
                modifier = Modifier.scale(0.8f),
                backgroundColor = Color.DesertSand,
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_outline_add_photo_alternate_24),
                    contentDescription = "Add Picturee",
                    modifier = Modifier.scale(1.4f)
                )
            }
            Spacer(Modifier.weight(1f))
            FloatingActionButton(
                onClick = { onEvent(EditNoteEvent.OnDeleteNote) },
                modifier = Modifier.scale(0.8f),
                backgroundColor = Color.DesertSand,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Add Picturee",
                    modifier = Modifier.scale(1.4f)
                )
            }
            Spacer(Modifier.weight(20f))
            FloatingActionButton(
                onClick = { onEvent(EditNoteEvent.OnSaveItem) },
                backgroundColor = Color.DesertSand,
                modifier = Modifier.scale(0.8f),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Check",
                    modifier = Modifier.scale(1.4f)
                )
            }
        }
    }
}

/*
@Preview
@Composable
fun NotePaperPreview() {
    NotePaper(
        title = "Preview",
        description = "PreviewDescription"
    ) {
        EditNoteEvent.OnSaveItem
    }
}*/

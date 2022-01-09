package com.himbrhms.checkapp.ui.editnotescreen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.ui.theme.DesertSand

@Composable
internal fun EditNoteSection(
    viewModel: EditNoteViewModel,
    onEvent: (ViewModelEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .weight(10f)
                    .padding(10.dp)
                    .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(elevation = 10.dp)
                    .background(viewModel.backgroundColor)
            ) {
                TextField(
                    value = viewModel.title,
                    onValueChange = { onEvent(ViewModelEvent.OnTitleChange(it)) },
                    placeholder = { Text(text = "Title", fontSize = 20.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = viewModel.backgroundColor
                    ),
                )
                TextField(
                    value = viewModel.description,
                    onValueChange = { onEvent(ViewModelEvent.OnDescriptionChange(it)) },
                    placeholder = { Text(text = "Notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f),
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = viewModel.backgroundColor
                    )
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp)
            ) {
                FloatingActionButton(
                    onClick = { onEvent(ViewModelEvent.OnToggleColorPickerBottomSheet) },
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
                    onClick = { onEvent(ViewModelEvent.OnAddImage) },
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
                    onClick = {
                        onEvent(ViewModelEvent.OnDeleteNotes)
                    },
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
                    onClick = { onEvent(ViewModelEvent.OnSaveNote) },
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
}
/*Column(
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
        onValueChange = { onEvent(ViewModelEvent.OnTitleChange(it)) },
        placeholder = { Text(text = "Title", fontSize = 20.sp) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = viewModel.backgroundColor
        ),
    )
    TextField(
        value = viewModel.description,
        onValueChange = { onEvent(ViewModelEvent.OnDescriptionChange(it)) },
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
            onClick = { onEvent(ViewModelEvent.OnToggleColorPickerBottomSheet) },
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
            onClick = { onEvent(ViewModelEvent.OnAddImage) },
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
            onClick = { },
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
            onClick = { onEvent(ViewModelEvent.OnSaveNote) },
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
}*/

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
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
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ContentChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ContentFocusChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleFocusChange
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.ui.util.HintTextField

@Composable
internal fun EditNoteSection(
    viewModel: EditNoteViewModel,
    onEvent: (ViewModelEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .shadow(elevation = 10.dp)
                .background(viewModel.backgroundColor)
                .weight(10f)
        ) {
            val titleState = viewModel.noteTitle.value
            val contentState = viewModel.noteContent.value
            HintTextField(
                text = titleState.text,
                textStyle = MaterialTheme.typography.h5,
                onValueChange = { onEvent(TitleChange(it)) },
                hint = titleState.hint,
                isHintVisible = titleState.isHintVisible,
                onFocusChange = { onEvent(TitleFocusChange(it))},
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                singleLine = true
            )
            HintTextField(
                text = contentState.text,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = { onEvent(ContentChange(it)) },
                hint = contentState.hint,
                isHintVisible = contentState.isHintVisible,
                onFocusChange = { onEvent(ContentFocusChange(it)) },
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            )
        }
        Row(
            modifier = Modifier
                .heightIn(min = 60.dp, max = 120.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp)
        ) {
            FloatingActionButton(
                onClick = { onEvent(ViewModelEvent.ToggleColorPickerBottomSheet) },
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
            Column() {
                FloatingActionButton(
                    onClick = {
                        onEvent(ViewModelEvent.DeleteSelectedNotes)
                    },
                    modifier = Modifier.scale(0.8f),
                    backgroundColor = Color.DesertSand,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.scale(1.4f)
                    )
                }
            }
            Spacer(Modifier.weight(20f))
            FloatingActionButton(
                onClick = { onEvent(ViewModelEvent.SaveNote) },
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

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
import com.himbrhms.checkapp.ui.categories.CategorySection
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ContentChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ContentFocusChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleChange
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.TitleFocusChange
import com.himbrhms.checkapp.ui.theme.DesertSand
import com.himbrhms.checkapp.ui.util.DesertFAB
import com.himbrhms.checkapp.ui.util.HintTextField

@ExperimentalMaterialApi
@Composable
internal fun EditNoteSection(
    viewModel: EditNoteViewModel,
    onEvent: (ViewModelEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
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
                onFocusChange = { onEvent(TitleFocusChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                singleLine = true
            )
            CategorySection(chips = listOf("Sport", "Food", "ADD"))
            HintTextField(
                text = contentState.text,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = { onEvent(ContentChange(it)) },
                hint = contentState.hint,
                isHintVisible = contentState.isHintVisible,
                onFocusChange = { onEvent(ContentFocusChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
        Row(
            modifier = Modifier
                .heightIn(min = 60.dp, max = 120.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(4.dp)
        ) {
            DesertFAB(
                onClick = { onEvent(ViewModelEvent.ColorPickerBottomSheet) },
                contentDescription = "Color Picker",
                drawableRes = R.drawable.ic_outline_color_lens_24
            )
            Spacer(Modifier.weight(1f))
            DesertFAB(
                onClick = { onEvent(ViewModelEvent.OnAddImage) },
                contentDescription = "Add Picture",
                drawableRes = R.drawable.ic_outline_add_photo_alternate_24
            )
            Spacer(Modifier.weight(1f))
            Column() {
                DesertFAB(
                    onClick = { onEvent(ViewModelEvent.DeleteSelectedNotes) },
                    contentDescription = "Delete",
                    drawableRes = R.drawable.ic_outline_delete_24
                )
            }
            Spacer(Modifier.weight(24f))
            DesertFAB(
                onClick = { onEvent(ViewModelEvent.SaveNote) },
                contentDescription = "Check",
                drawableRes = R.drawable.ic_baseline_check_24
            )
        }
    }
}

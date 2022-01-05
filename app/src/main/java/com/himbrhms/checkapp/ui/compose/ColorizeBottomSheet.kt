package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.model.EditNoteViewModel
import com.himbrhms.checkapp.model.events.EditNoteEvent.OnColorChange
import com.himbrhms.checkapp.ui.theme.*

@ExperimentalMaterialApi
var bottomSheetState: ModalBottomSheetState? = null

@ExperimentalMaterialApi
@Composable
fun ColorizeBottomSheet() {
    bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState!!,
        sheetContent = {
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f)) {
                Spacer(Modifier.weight(0.5f))
                ColorCircle(Color.White)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.Cream)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.DaySkyBlue)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.LightCoral)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.LightCyan)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.LightDesertSand)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.PigPink)
                Spacer(Modifier.weight(1f))
                ColorCircle(Color.MagicMind)
                Spacer(Modifier.weight(0.5f))
            }
        }) {
    }
}

@Composable
private fun ColorCircle(fillColor: Color, viewModel: EditNoteViewModel = hiltViewModel()) {
    FloatingActionButton(onClick = { viewModel.onEditNoteEvent(OnColorChange(fillColor)) },
        modifier= Modifier
            .size(40.dp)
            .border(1.dp, Color.LightGray, CircleShape),  //avoid the oval shape
        shape = CircleShape,
        backgroundColor = fillColor
    ){}
}

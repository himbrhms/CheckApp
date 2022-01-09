package com.himbrhms.checkapp.ui.editnotescreen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent.ColorChange
import com.himbrhms.checkapp.ui.theme.*

@ExperimentalMaterialApi
internal var colorBottomSheetState: ModalBottomSheetState? = null

@ExperimentalMaterialApi
@Composable
internal fun ColorPickerBottomSheet() {
    colorBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = colorBottomSheetState!!,
        sheetContent = {
            Spacer(modifier = Modifier.size(10.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(4.dp)) {
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
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
        }) {
    }
}

@Composable
private fun ColorCircle(backgroundColor: Color, viewModel: EditNoteViewModel = hiltViewModel()) {
    Button(onClick = { viewModel.onEvent(ColorChange(backgroundColor)) },
        modifier= Modifier
            .size(40.dp)
            .border(1.dp, Color.LightGray, CircleShape),  //avoid the oval shape
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor)
    ){}
}

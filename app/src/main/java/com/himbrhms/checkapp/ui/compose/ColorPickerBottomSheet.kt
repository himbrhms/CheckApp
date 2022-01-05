package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ColorPickerBottomSheet() {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Text(text = "Hello from sheet")
            }
        }) {
        IconButton(
            modifier = Modifier.offset(100.dp),
            onClick = {
                coroutineScope.launch {
                    if (bottomSheetState.isVisible) {
                        bottomSheetState.hide()
                    } else {
                        bottomSheetState.show()
                    }
                }
            }) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Localized description",
                tint = Color.Red
            )
        }
    }
}

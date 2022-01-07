package com.himbrhms.checkapp.ui.notelistscreen

import androidx.compose.material.*
import androidx.compose.runtime.Composable

@ExperimentalMaterialApi
internal var noteListBottomSheetState: ModalBottomSheetState? = null

@ExperimentalMaterialApi
@Composable
internal fun NoteListBottomSheet() {
    noteListBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = noteListBottomSheetState!!,
        sheetContent = {
            Text("Hier könnte ihre Werbung stehen")
        }) {
    }
}

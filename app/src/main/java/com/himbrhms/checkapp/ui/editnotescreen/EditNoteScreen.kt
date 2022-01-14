package com.himbrhms.checkapp.ui.editnotescreen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowHideColorPickerSheet
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowHideGroupingBottomSheet
import com.himbrhms.checkapp.viewmodel.events.UiEvent.PopBackStack
import com.himbrhms.checkapp.viewmodel.events.UiEvent.ShowToast
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@Composable
fun EditNoteScreen(
    onPopBackStack: () -> Unit,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is PopBackStack -> onPopBackStack()
                is ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is ShowHideColorPickerSheet -> {
                    colorBottomSheetState?.let { sheet ->
                        if (sheet.isVisible) sheet.hide() else sheet.show()
                    }
                }
                is ShowHideGroupingBottomSheet -> {
                    groupingBottomSheetState?.let { sheet ->
                        if (sheet.isVisible) sheet.hide() else sheet.show()
                    }
                }
                else -> Unit
            }
        }
    }
    SaveOnBackPressed { event ->
        viewModel.onEvent(event)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        EditNoteSection(viewModel) { event ->
            viewModel.onEvent(event)
        }
        ColorPickerBottomSheet()
        GroupingBottomSheet()
    }
}

@Composable
private fun SaveOnBackPressed(onEventCallback: (ViewModelEvent) -> Unit) {
    BackHandler {
        onEventCallback(ViewModelEvent.SaveNote)
    }
}

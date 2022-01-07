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
import com.himbrhms.checkapp.model.events.UiEvent.OnShowHideColorPickerSheet
import com.himbrhms.checkapp.model.events.UiEvent.OnPopBackstack
import com.himbrhms.checkapp.model.events.UiEvent.OnShowToast
import com.himbrhms.checkapp.model.EditNoteViewModel
import com.himbrhms.checkapp.model.events.ModelEvent
import com.himbrhms.checkapp.util.Logger
import kotlinx.coroutines.flow.collect

@ExperimentalMaterialApi
@Composable
fun EditNoteScreen(
    onPopBackStack: () -> Unit,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val logger = Logger("EditItemScreen")
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OnPopBackstack -> onPopBackStack()
                is OnShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is OnShowHideColorPickerSheet -> {
                    logger.info("ColorizeBottomSheetEvent: isVisible=${colorBottomSheetState?.isVisible}")
                    if (colorBottomSheetState?.isVisible == true) {
                        colorBottomSheetState?.hide()
                    } else if (colorBottomSheetState?.isVisible == false) {
                        colorBottomSheetState?.show()
                    }
                }
                else -> Unit
            }
        }
    }
    SaveOnBackPressed { event ->
        viewModel.onEditNoteEvent(event)
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        EditNoteColumn(viewModel) { event ->
            viewModel.onEditNoteEvent(event)
        }
        ColorPickerBottomSheet()
    }
}

@Composable
private fun SaveOnBackPressed(onEventCallback: (ModelEvent) -> Unit) {
    BackHandler {
        onEventCallback(ModelEvent.OnSaveNote)
    }
}

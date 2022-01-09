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
import androidx.lifecycle.viewModelScope
import com.himbrhms.checkapp.viewmodel.events.UiEvent.OnShowHideColorPickerSheet
import com.himbrhms.checkapp.viewmodel.events.UiEvent.OnPopBackstack
import com.himbrhms.checkapp.viewmodel.events.UiEvent.OnShowToast
import com.himbrhms.checkapp.viewmodel.EditNoteViewModel
import com.himbrhms.checkapp.viewmodel.events.ViewModelEvent
import com.himbrhms.checkapp.util.Logger
import com.himbrhms.checkapp.viewmodel.events.UiEvent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
    }
}

@Composable
private fun SaveOnBackPressed(onEventCallback: (ViewModelEvent) -> Unit) {
    BackHandler {
        onEventCallback(ViewModelEvent.OnSaveNote)
    }
}

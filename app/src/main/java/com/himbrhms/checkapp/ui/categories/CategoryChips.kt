package com.himbrhms.checkapp.ui.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.ui.theme.PleassantWhite
import com.himbrhms.checkapp.ui.util.HintTextField
import com.himbrhms.checkapp.ui.util.IconTextField

@Composable
fun CategorySection(
    chips: List<String>
) {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    LazyRow {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) Color.Blue
                        else Color.Gray.copy(alpha = 0.6f)
                    )
                    .padding(8.dp)
            ) {
                Text(text = chips[it], color = Color.White)
            }
        }
        items(1) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {}
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.Gray.copy(alpha = 0.6f))
                    .padding(8.dp)
            ) {
                IconTextField(
                    hintIcon = R.drawable.ic_outline_add_circle_outline_24,
                    textStyle = TextStyle(color = Color.PleassantWhite),
                    onValueChange = {},
                    onFocusChange = {})
            }
        }
    }
}
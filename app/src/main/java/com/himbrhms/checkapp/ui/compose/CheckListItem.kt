package com.himbrhms.checkapp.ui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.himbrhms.checkapp.data.CheckListItemData
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himbrhms.checkapp.common.events.CheckListEvent

@Composable
fun CheckListItem(
    item: CheckListItemData,
    onEvent: (CheckListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .border(BorderStroke(2.dp, Color.Black))
            .clip(RoundedCornerShape(4.dp))
            .background(Color(item.backColorValue))
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = {
                        onEvent(CheckListEvent.OnDeleteItem(item))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
                item.description.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it)
                }
            }
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { isChecked ->
                    onEvent(CheckListEvent.OnChangeChecked(item, isChecked))
                }
            )
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    CheckListItem(
        CheckListItemData(
            id = 1,
            title = "Preview",
            description = "PreviewDescription",
            true
        ),
        {
            CheckListEvent.OnAddItem
        }
    )
}

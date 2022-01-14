package com.himbrhms.checkapp.ui.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.ui.theme.DesertSand

@Composable
fun DesertFAB(
    @DrawableRes drawableRes: Int,
    contentDescription: String = "",
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .scale(0.8f)
            .border(width = 1.dp, color = Color.Gray, shape = CircleShape),
        backgroundColor = Color.DesertSand,
    ) {
        Icon(
            painterResource(drawableRes),
            contentDescription = contentDescription,
            modifier = Modifier.scale(1.4f)
        )
    }
}

@Preview
@Composable
fun DesertFabPreview() {
    DesertFAB(R.drawable.ic_baseline_add_photo_alternate_24) {}
}
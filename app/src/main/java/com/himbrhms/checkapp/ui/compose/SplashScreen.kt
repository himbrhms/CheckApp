package com.himbrhms.checkapp.ui.compose

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.himbrhms.checkapp.R
import com.himbrhms.checkapp.common.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(1000L)
        navController.navigate(Routes.CHECKLIST_SCREEN) {
            popUpTo(Routes.SPLASH_SCREEN) { // remove SplashScreen from backstack
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_checkapp),
                contentDescription = "CheckApp Logo",
                modifier = Modifier.scale(scale.value)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_name_checkapp),
                contentDescription = "CheckApp Name Logo",
                modifier = Modifier.scale(2f),
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.BottomCenter
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Powered by himbrhms",
            modifier = Modifier.padding(16.dp),
        )
    }
}

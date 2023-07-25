package com.binayshaw7777.readbud.components.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun NavigationAnimation(content: @Composable () -> Unit) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = 250,
                delayMillis = 0
            ),
            initialOffsetX = {
                it
            }
        ),
        //TODO: Exit animation does not works for now
//        exit = slideOutHorizontally(
//            animationSpec = tween(
//                durationMillis = 250,
//                delayMillis = 0
//            ),
//            targetOffsetX = {
//                0
//            }
//        )
        exit = slideOutHorizontally(
            animationSpec = tween(
                200,
                delayMillis = 200
            )
        ) + shrinkHorizontally() + fadeOut()
    ) {
        content()
    }
}
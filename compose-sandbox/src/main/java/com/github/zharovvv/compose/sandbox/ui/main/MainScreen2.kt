package com.github.zharovvv.compose.sandbox.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.github.zharovvv.compose.sandbox.ext.compose.distinctUntilChanged
import com.github.zharovvv.compose.sandbox.ui.pager.AnimatedPager
import com.github.zharovvv.compose.sandbox.ui.pager.DraggableSurface
import com.github.zharovvv.compose.sandbox.ui.pager.StubItem
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen2() {
    val data = remember {
        listOf("first", "second", "third", "fourth")
    }
    Scaffold(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp), //отступы статусбара и навбара
        containerColor = MaterialTheme.colorScheme.primary
    ) { contentPadding ->
        val dragZoneState = remember {
            mutableStateOf(DragZoneState.NO_DRAG)
                .distinctUntilChanged()
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center
        ) {
            val haptic = LocalHapticFeedback.current
            LaunchedEffect(key1 = data) {
                snapshotFlow { dragZoneState.value }
                    .filter { it == DragZoneState.ON_THREE_QUARTERS }
                    .onEach {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                    .launchIn(this)
            }
            Column {
                AnimatedPager(count = data.size, contentPadding = contentPadding) { page: Int ->
                    val maxTopOffset = remember { 25.dp }
                    val maxBottomOffset = remember { 35.dp }
                    DraggableSurface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(
                                start = 0.dp,
                                top = maxTopOffset,
                                end = 0.dp,
                                bottom = maxBottomOffset
                            ),
                        containerModifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(horizontal = 18.dp, vertical = 32.dp),
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surface,
                        elevation = 1.dp,
                        maxTopOffset = maxTopOffset,
                        maxBottomOffset = maxBottomOffset,
                        dragProgressUp = { progress -> },
                        dragProgressDown = { progress ->
                            when {
                                progress < 0.25f -> dragZoneState.value = DragZoneState.NO_DRAG
                                progress >= 0.25f && progress < 0.5f -> dragZoneState.value =
                                    DragZoneState.ON_QUARTER
                                progress >= 0.5f && progress < 0.75f -> dragZoneState.value =
                                    DragZoneState.ON_HALF
                                progress >= 0.75f && progress < 0.99f -> dragZoneState.value =
                                    DragZoneState.ON_THREE_QUARTERS
                                progress >= 0.99f -> dragZoneState.value = DragZoneState.ON_MAX
                            }
                        },
                        onDragStopped = {
                            dragZoneState.value = DragZoneState.NO_DRAG
                        }
                    ) {
                        StubItem(content = data[page])
                    }
                }
                val animationDescription = remember {
                    mutableStateOf("")
                }
                when (dragZoneState.value) {
                    DragZoneState.NO_DRAG -> {
                        animationDescription.value = ""
                    }
                    DragZoneState.ON_QUARTER, DragZoneState.ON_HALF -> {
                        animationDescription.value = "Потяните вниз чтобы..."
                    }
                    DragZoneState.ON_THREE_QUARTERS, DragZoneState.ON_MAX -> {
                        animationDescription.value = "Отпустите чтобы..."
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = animationDescription.value)
                }
            }
        }
    }
}

enum class DragZoneState {
    NO_DRAG,
    ON_QUARTER,
    ON_HALF,
    ON_THREE_QUARTERS,
    ON_MAX
}

const val TAG = "MainScreen2"
package com.seo4d696b75.android.switchbot_lock_ext.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.animation.VectorConverter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.seo4d696b75.android.switchbot_lock_ext.ui.R
import com.seo4d696b75.android.switchbot_lock_ext.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Float.max
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun AnimatedToggle(
    checked: Boolean,
    label: @Composable RowScope.(Boolean) -> Unit,
    loadingLabel: @Composable RowScope.(Boolean) -> Unit,
    icon: @Composable (Boolean) -> Unit,
    contentColor: (Boolean) -> Color,
    onCheckedChange: suspend (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    thumbColor: Color = Color.White,
) {
    val scope = rememberCoroutineScope()
    val width = 120.dp
    val height = 45.dp
    val thumbSize = 45.dp
    val density = LocalDensity.current
    val maxOffset = with(density) { (width - thumbSize).toPx() }

    val state = rememberAnimatedToggleState(
        checked = checked,
        initialColor = contentColor(false),
        targetColor = contentColor(true),
    )

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            state.slide.snapTo(
                if (state.isChecked) 1f else 0f,
            )
        }
    }

    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(
                color = state.currentColor,
                shape = CircleShape,
            )
            .clip(CircleShape),
    ) {
        // label
        Crossfade(
            targetState = state.isLoading to state.isChecked,
            label = "label crossfade"
        ) { pair ->
            val (isLoading, isChecked) = pair
            if (isLoading) {
                Row(
                    modifier = Modifier
                        .padding(
                            start = if (isChecked) thumbSize else 0.dp,
                            end = if (isChecked) 0.dp else thumbSize,
                        )
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    loadingLabel(!isChecked)
                }
            } else {
                if (isChecked) {
                    Row(
                        modifier = Modifier
                            .padding(end = thumbSize)
                            .fillMaxSize()
                            .offset {
                                IntOffset(
                                    x = (-0.5f * (1f - state.slide.value) * maxOffset).roundToInt(),
                                    y = 0,
                                )
                            }
                            .alpha(state.slide.value),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        label(true)
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .padding(start = thumbSize)
                            .fillMaxSize()
                            .offset {
                                IntOffset(
                                    x = (0.5f * state.slide.value * maxOffset).roundToInt(),
                                    y = 0,
                                )
                            }
                            .alpha(1f - state.slide.value),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        label(false)
                    }
                }
            }
        }

        // thumb
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset {
                    IntOffset(
                        x = (state.slide.value * maxOffset).roundToInt(),
                        y = 0,
                    )
                }
                .draggable(
                    enabled = !state.isLoading,
                    state = rememberDraggableState { delta ->
                        val pixel = state.slide.value * maxOffset + delta
                        val target = max(min(pixel / maxOffset, 1f), 0f)
                        scope.launch {
                            state.slide.snapTo(target)
                        }
                        if (target == 1f && !state.isChecked && !state.isLoading) {
                            scope.launch {
                                state.isLoading = true
                                runCatching {
                                    onCheckedChange(true)
                                }
                                state.isLoading = false
                            }
                        } else if (target == 0f && state.isChecked && !state.isLoading) {
                            scope.launch {
                                state.isLoading = true
                                runCatching {
                                    onCheckedChange(false)
                                }
                                state.isLoading = false
                            }
                        }
                    },
                    orientation = Orientation.Horizontal,
                    onDragStopped = {
                        if (!state.isLoading) {
                            scope.launch {
                                state.slide.animateTo(
                                    targetValue = if (state.isChecked) 1f else 0f,
                                    animationSpec = tween(
                                        durationMillis = 200,
                                        easing = EaseOutCubic,
                                    )
                                )
                            }
                        }
                    }
                )
                .border(
                    width = 4.dp,
                    color = state.currentColor,
                    shape = CircleShape,
                )
                .padding(4.dp)
                .background(
                    color = thumbColor,
                    shape = CircleShape,
                ),
        ) {
            Crossfade(
                targetState = state.isLoading to state.isChecked,
                label = "thumb crossfade",
                modifier = Modifier.wrapContentSize(),
            ) { pair ->
                val (isLoading, isChecked) = pair
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(thumbSize - 20.dp),
                            color = contentColor(!isChecked),
                            strokeWidth = 3.dp,
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(1f - state.slide.value),
                        contentAlignment = Alignment.Center,
                    ) {
                        icon(false)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(state.slide.value),
                        contentAlignment = Alignment.Center,
                    ) {
                        icon(true)
                    }
                }
            }
        }
    }
}

@Stable
private data class AnimatedToggleState(
    private var _isChecked: Boolean = false,
    private val _isLoading: MutableState<Boolean> = mutableStateOf(false),
    val slide: Animatable<Float, AnimationVector1D>, // normalized in [0,1]
    val colorConverter: TwoWayConverter<Color, AnimationVector4D>,
    val initialColorVec: AnimationVector4D,
    val targetColorVec: AnimationVector4D,
) {
    var isLoading by _isLoading

    val isChecked: Boolean
        get() = _isChecked

    fun computeIsChecked(hoistedState: Boolean) {
        if (!isLoading) {
            _isChecked = hoistedState
        }
    }

    val currentColor: Color
        get() {
            val colorVec = with(slide.value) {
                AnimationVector4D(
                    (1f - this) * initialColorVec.v1 + this * targetColorVec.v1,
                    (1f - this) * initialColorVec.v2 + this * targetColorVec.v2,
                    (1f - this) * initialColorVec.v3 + this * targetColorVec.v3,
                    (1f - this) * initialColorVec.v4 + this * targetColorVec.v4,
                )
            }
            return colorConverter.convertFromVector(colorVec)
        }
}

@Composable
private fun rememberAnimatedToggleState(
    checked: Boolean,
    initialColor: Color,
    targetColor: Color,
): AnimatedToggleState {
    return remember {
        val converter = (Color.VectorConverter)(initialColor.colorSpace)
        AnimatedToggleState(
            slide = Animatable(0f),
            colorConverter = converter,
            initialColorVec = converter.convertToVector(initialColor),
            targetColorVec = converter.convertToVector(targetColor),
        )
    }.apply { computeIsChecked(checked) }
}

@Preview
@Composable
private fun AnimatedTogglePreview() {
    AppTheme {
        var isChecked by remember {
            mutableStateOf(false)
        }
        AnimatedToggle(
            checked = isChecked,
            label = {
                if (it) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.White,
                    )
                }
                Text(
                    text = if (it) "Unlock" else "Lock",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                if (!it) {
                    Icon(
                        Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.White,
                    )
                }
            },
            loadingLabel = {
                Text(
                    text = if (it) "Locking..." else "Unlocking...",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
            },
            icon = {
                Icon(
                    painter = painterResource(
                        id = if (it) R.drawable.ic_lock else R.drawable.ic_unlock,
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = if (it) Color.Blue else Color.Gray,
                )
            },
            contentColor = { if (it) Color.Blue else Color.Gray },
            onCheckedChange = {
                delay(1000)
                isChecked = it
            },
        )
    }
}

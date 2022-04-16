package m.derakhshan.swipablecards

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun Card(
    modifier: Modifier = Modifier,
    sentence: String,
    zIndex: Float,
    swipedListener: () -> Unit
) {

    var origin by remember { mutableStateOf(0f) }

    var rotateRaw by remember { mutableStateOf(0f) }
    val rotate by animateFloatAsState(targetValue = rotateRaw)

    var offsetRaw by remember { mutableStateOf(0.dp) }
    val offset by animateDpAsState(targetValue = offsetRaw)

    var alphaRaw by remember { mutableStateOf(1f) }
    val alpha by animateFloatAsState(targetValue = alphaRaw)

    var scaleRaw by remember { mutableStateOf(1f) }

    val scale by animateFloatAsState(targetValue = scaleRaw)

    var resetInitValue by remember { mutableStateOf(mapOf(true to false)) }

    LaunchedEffect(resetInitValue) {
        alphaRaw = 0f
        if (resetInitValue.values.first())
            delay(150)
        rotateRaw = 0f
        scaleRaw  = if (zIndex == 1f) 1f else 0.8f
        offsetRaw = 0.dp
        origin = 0f
        if (resetInitValue.values.first())
            delay(150)
        alphaRaw = 1f
    }

    LaunchedEffect(zIndex) { scaleRaw = if (zIndex == 1f) 1f else 0.8f }

    Box(
        modifier = Modifier
            .rotate(rotate)
            .offset(x = offset, y = offset / 10)
            .scale(scale)
            .zIndex(zIndex)
            .alpha(alpha)
            .draggable(
                state = rememberDraggableState(onDelta = { delta ->
                    origin += delta
                    rotateRaw += delta * 0.02f
                    offsetRaw += (delta * 0.5).dp
                    if (origin > 0) {
                        alphaRaw -= delta * 0.001f
                        scaleRaw += delta * 0.0003f
                    } else {
                        alphaRaw += delta * 0.001f
                        scaleRaw -= delta * 0.0003f
                    }
                }),
                orientation = Orientation.Horizontal,
                onDragStopped = {
                    resetInitValue = if (abs(rotate) > 6) {
                        swipedListener()
                        mapOf(!resetInitValue.keys.first() to true)
                    } else
                        mapOf(!resetInitValue.keys.first() to false)
                }
            )
    ) {
        Column(
            modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = sentence)
        }
    }
}
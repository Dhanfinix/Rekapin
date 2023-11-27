package com.dhandev.rekapin.presentation.ui.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.MyGreen
import com.dhandev.rekapin.ui.theme.MyRed
import com.dhandev.rekapin.ui.theme.RekapinTheme
import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun Chart(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    inputValues: List<Float>,
    animated: Boolean = true,
    enableClickInfo: Boolean = true,
    category: List<String>
){
    val chartDegrees = 360f // circle shape

    // start drawing clockwise (top to right)
    var startAngle = 270f

    // calculate each input percentage
    val proportions = inputValues.map {
        it * 100 / inputValues.sum()
    }

    // calculate each input slice degrees
    val angleProgress = proportions.map { prop ->
        chartDegrees * prop / 100
    }
    // clicked slice index
    val clickedItemIndex = remember {
        mutableIntStateOf(0)
    }

    // calculate each slice end point in degrees, for handling click position
    val progressSize = mutableListOf<Float>()

    LaunchedEffect(angleProgress){
        progressSize.add(angleProgress.first())
        for (x in 1 until angleProgress.size) {
            progressSize.add(angleProgress[x] + progressSize[x - 1])
        }
    }
    // text style
    val density = LocalDensity.current
    val textFontSize = with(density) { 30.dp.toPx() }
    val textPaint = remember {
        Paint().apply {
            color = colors[clickedItemIndex.intValue].toArgb()
            textSize = textFontSize
            textAlign = Paint.Align.CENTER
        }
    }
    LaunchedEffect(clickedItemIndex.intValue) {
        textPaint.color = colors[clickedItemIndex.intValue].toArgb()
    }
    val sliceWidth = with(LocalDensity.current) { 30.dp.toPx() }
    val selectedSliceWidth = with(LocalDensity.current) { 40.dp.toPx() }

    BoxWithConstraints(modifier = modifier.padding(30.dp), contentAlignment = Alignment.Center) {

        val canvasSize = min(constraints.maxWidth, constraints.maxHeight)
        val size = Size(canvasSize.toFloat(), canvasSize.toFloat())
        val canvasSizeDp = with(LocalDensity.current) { canvasSize.toDp() }

        Canvas(
            modifier = Modifier
                .size(canvasSizeDp)
                .pointerInput(inputValues) {
                    detectTapGestures { offset ->
                        val clickedAngle = touchPointToAngle(
                            width = canvasSize.toFloat(),
                            height = canvasSize.toFloat(),
                            touchX = offset.x,
                            touchY = offset.y,
                            chartDegrees = chartDegrees
                        )
                        progressSize.forEachIndexed { index, item ->
                            if (clickedAngle <= item) {
                                clickedItemIndex.intValue = index
                                return@detectTapGestures
                            }
                        }
                    }
                }
        ) {

            angleProgress.forEachIndexed { index, angle ->
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = if (index == clickedItemIndex.intValue) selectedSliceWidth else sliceWidth)
                )
                startAngle += angle
            }

            if (clickedItemIndex.intValue != -1) {
                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawText(
                        category[clickedItemIndex.intValue],
                        (canvasSize / 2) + textFontSize / 4,
                        (canvasSize / 2) + 0f,
                        textPaint
                    )
                    canvas.nativeCanvas.drawText(
                        "${proportions[clickedItemIndex.intValue].roundToInt()}%",
                        (canvasSize / 2) + textFontSize / 4,
                        (canvasSize / 2) + textFontSize,
                        textPaint
                    )
                }
            }
        }

    }
}
private fun touchPointToAngle(
    width: Float,
    height: Float,
    touchX: Float,
    touchY: Float,
    chartDegrees: Float
): Double {
    val x = touchX - (width * 0.5f)
    val y = touchY - (height * 0.5f)
    var angle = Math.toDegrees(atan2(y.toDouble(), x.toDouble()) + Math.PI / 2)
    angle = if (angle < 0) angle + chartDegrees else angle
    return angle
}

@Preview
@Composable
fun PreviewChart(){
    val chartColors = listOf(
        MyRed,
        MyGreen,
        BlueMain,
        BlueSecondary
    )
    val chartValues = listOf(20_000f, 100_000f, 200_000f, 30_000f)
    val chartCategory = listOf("Makan & Minum", "Transportasi", "Donasi", "Kesehatan")

    RekapinTheme {
        Surface {
            Chart(
                modifier = Modifier.padding(20.dp),
                colors = chartColors,
                inputValues = chartValues,
                category = chartCategory
            )
        }
    }
}
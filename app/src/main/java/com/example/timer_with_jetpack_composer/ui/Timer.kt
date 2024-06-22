package com.example.timer_with_jetpack_composer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun Timer(
    totalTime: Long,
    handleColor: Color,
    inactiveBarColor: Color,
    activeBarColor: Color,
    modifier: Modifier = Modifier,
    initialValue : Float = 1f,
    radius: Dp = 80.dp,
    strokeWidth: Dp = 8.dp,
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var value by remember { mutableFloatStateOf(initialValue) }
    var currentTime by remember { mutableLongStateOf(totalTime) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect (key1 = currentTime, key2 = isTimerRunning) {
        if(currentTime > 0 && isTimerRunning) {
            delay(100L)
            currentTime -= 100L
            value = currentTime / totalTime.toFloat()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.onSizeChanged { size = it }
    ){

        Canvas(modifier=modifier){
            drawArc(
                color = inactiveBarColor,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false, //TODO: check if it is needed
                size = Size(size.width.toFloat(),size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = activeBarColor,
                startAngle = -215f,
                sweepAngle = 250f*value,
                useCenter = false, //TODO: check if it is needed
                size = Size(size.width.toFloat(),size.height.toFloat()),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = handleColor,
                startAngle = -215f+250f*value-0.1f,
                sweepAngle = 0.1f,
                useCenter = false, //TODO: check if it is needed
                size = Size(size.width.toFloat(),size.height.toFloat()),
                style = Stroke(10.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = (currentTime/1000L).toString(),
            fontSize = 44.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )





        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(10.dp))
                .graphicsLayer {
                    shape = CutCornerShape(0.dp)
                    clip = false
                }
                .background(Color.Transparent)
            ,
            colors = if(!isTimerRunning) ButtonColors(
                Color.Green,
                Color.Green,
                Color.Green,
                Color.Green) else ButtonColors(
                Color.Red,
                Color.Red,
                Color.Red,
                Color.Red)
            ,
            onClick = {

                if(currentTime<=0L){
                    currentTime = totalTime
                    isTimerRunning = true
                } else{
                    isTimerRunning = !isTimerRunning
                }

            }
        ) {
            Text(text = if (isTimerRunning) "Pause" else "Start",
                color = Color.Black)

        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    Surface(
        color = Color(0xFF121212),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ){
            Timer(
                totalTime = 100L * 1000L,
                handleColor = Color.White,
                inactiveBarColor = Color.DarkGray,
                activeBarColor = Color.Green,
                modifier = Modifier
                    .size(200.dp)
            )
        }
    }
}
package co.nexlabs.betterhr.joblanding.android.data

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.core.graphics.drawable.toDrawable

@Composable
fun SignaturePad(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 4f,
    strokeColor: Color = Color.Black,
    onSave: (Bitmap) -> Unit
) {
    var path by remember { mutableStateOf(Path()) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var canvasWidth by remember { mutableStateOf(0) }
    var canvasHeight by remember { mutableStateOf(0) }

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    canvasWidth = size.width
                    canvasHeight = size.height
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { offset ->
                            path.moveTo(offset.x, offset.y)
                            offsetX = offset.x
                            offsetY = offset.y
                        },
                        onDrag = { change, dragAmount ->
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                            path.lineTo(offsetX, offsetY)
                        }
                    )
                }
                .background(Color.White)
        ) {
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    color = strokeColor
                    /*strokeWidth = strokeWidth
                    style = Paint.Style.STROKE*/
                    strokeCap = StrokeCap.Round
                    strokeJoin = StrokeJoin.Round
                    isAntiAlias = true
                }
                canvas.drawPath(path, paint)
            }
        }
        /*Button(
            onClick = {
                val bitmap = Bitmap.createBitmap(canvasWidth, canvasHeight, Bitmap.Config.ARGB_8888)
                val androidCanvas = AndroidCanvas(bitmap)
                androidCanvas.drawPath(path.asAndroidBitmap().asFrameworkPath(), Paint().apply {
                    color = strokeColor.toArgb()
                    strokeWidth = strokeWidth
                    style = android.graphics.Paint.Style.STROKE
                    strokeCap = android.graphics.Paint.Cap.ROUND
                    strokeJoin = android.graphics.Paint.Join.ROUND
                    isAntiAlias = true
                })
                onSave(bitmap)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text(text = "Save Signature")
        }*/
    }
}


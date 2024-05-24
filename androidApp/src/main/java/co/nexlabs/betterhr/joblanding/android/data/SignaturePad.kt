/*
package co.nexlabs.betterhr.joblanding.android.data

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.bottomBarVisible
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.joelkanyi.composesignature.ComposeSignature
import io.github.joelkanyi.sain.Sain
import io.github.joelkanyi.sain.SignatureAction
import io.github.joelkanyi.sain.SignatureState
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun SignaturePad() {
    var applicationContext = LocalContext.current.applicationContext
    var uri: Uri? by remember { mutableStateOf(null) }
    var imageBitmap: ImageBitmap? by remember {
        mutableStateOf(null)
    }

    val state = remember {
        SignatureState()
    }

    var showSignatureDraw by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!showSignatureDraw) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(502.dp)
                    .background(
                        color = Color(0xFFF0F8FE),
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        1.dp,
                        Color(0xFFF0F8FE),
                        RoundedCornerShape(4.dp)
                    )
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                when (event.type) {
                                    PointerEventType.Press -> {
                                        showSignatureDraw = true
                                    }
                                    */
/*PointerEventType.Release -> {
                                        println("Touch up at: ${event.changes.first().position}")
                                    }
                                    PointerEventType.Move -> {
                                        println("Touch move at: ${event.changes.first().position}")
                                    }*//*

                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(applicationContext)
                        .data(R.drawable.apply_job_success)
                        .decoderFactory { result, options, _ ->
                            ImageDecoderDecoder(
                                result.source,
                                options
                            )
                        }
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = "GIF",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .height(40.dp)
                    .clickable {
                        bottomBarVisible.postValue(false)
                    }
                    .background(color = Color.Transparent)
                    .border(
                        1.dp,
                        color = Color(0xFF1ED292),
                        RoundedCornerShape(8.dp)
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Cancel",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFAAAAAA),
                        fontSize = 14.sp,
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(
                            color = Color(0xFF1ED292),
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            1.dp,
                            color = Color(0xFF1ED292),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable {
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Next",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                    )
                }
            }
        } else {
            Sain(
                state = state,
                signatureThickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(502.dp)
                    .background(
                        color = Color(0xFFF0F8FE),
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        1.dp,
                        Color(0xFFF0F8FE),
                        RoundedCornerShape(4.dp)
                    ),
                onComplete = { signatureBitmap ->
                    if (signatureBitmap != null) {
                        imageBitmap = signatureBitmap
                    } else {
                        println("Signature is empty")
                    }
                },
            ) { action ->

                Image(
                    painter = painterResource(id = R.drawable.sign_undo),
                    contentDescription = "Undo Image",
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(32.dp).align(Alignment.End)
                        .clickable {
                            action(SignatureAction.CLEAR)
                            uri = null
                        }
                )

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .clickable {
                            bottomBarVisible.postValue(false)
                        }
                        .background(color = Color.Transparent)
                        .border(
                            1.dp,
                            color = Color(0xFF1ED292),
                            RoundedCornerShape(8.dp)
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFAAAAAA),
                            fontSize = 14.sp,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color = Color(0xFF1ED292),
                                shape = MaterialTheme.shapes.medium
                            )
                            .border(
                                1.dp,
                                color = Color(0xFF1ED292),
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                //upload draw sign & response offer
                                //check file empty or not
                                */
/*scope.launch {
                                viewModel.uploadSingleFile(

                                )*//*

                                action(SignatureAction.COMPLETE)
                                uri = saveBitmapAsFile(
                                    applicationContext,
                                    imageBitmap!!.asAndroidBitmap(),
                                    "signature.png"
                                )
                                Log.d("nit>>", uri.toString())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Next",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}

private fun saveBitmapAsFile(context: Context, bitmap: Bitmap, fileName: String): Uri? {
    val file = File(context.cacheDir, fileName)
    return try {
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.flush()
        stream.close()
        Uri.fromFile(file)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}*/

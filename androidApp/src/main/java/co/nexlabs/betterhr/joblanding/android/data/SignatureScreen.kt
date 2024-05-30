//package co.nexlabs.betterhr.joblanding.android.data
//
//import android.graphics.Bitmap
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.core.graphics.drawable.toDrawable
//
//@Composable
//fun SignatureScreen() {
//    val context = LocalContext.current
//    SignaturePad(
//        modifier = Modifier.fillMaxSize(),
//        onSave = { bitmap ->
//            // Handle the saved bitmap here
//            // For example, you can save it to a file or display it in an ImageView
//            val drawable = bitmap.toDrawable(context.resources)
//            // Display or save the drawable as needed
//        }
//    )
//}
//
//
///*
//@Composable
//fun SignatureScreen() {
//    val context = LocalContext.current
//    SignaturePad(
//        modifier = Modifier.fillMaxSize(),
//        onSave = { bitmap ->
//            // Handle the saved bitmap here
//            val drawable = bitmap.toDrawable(context.resources)
//            // Display or save the drawable as needed
//        }
//    )
//}*/

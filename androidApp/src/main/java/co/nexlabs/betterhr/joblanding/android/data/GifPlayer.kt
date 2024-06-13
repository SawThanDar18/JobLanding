package co.nexlabs.betterhr.joblanding.android.data

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import co.nexlabs.betterhr.joblanding.android.R
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun GifPlayer() {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(R.drawable.apply_job_success)
            .decoderFactory { result, options, _ -> ImageDecoderDecoder(result.source, options) }
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}
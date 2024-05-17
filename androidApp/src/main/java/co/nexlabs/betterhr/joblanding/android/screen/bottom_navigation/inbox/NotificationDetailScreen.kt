package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxDetailViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.util.isContainWithThese
import co.nexlabs.betterhr.joblanding.util.isStartWithThese
import kotlinx.coroutines.launch
import java.net.URLEncoder

@Composable
fun NotificationDetailScreen(
    viewModel: InboxDetailViewModel,
    navController: NavController,
    notificationId: String,
    notiType: String
) {
    var context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    var loading by remember { mutableStateOf(false) }

    scope.launch {
        if (notificationId != "") {
            viewModel.fetchNotificationDetail(notificationId)
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            uiState.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator(
                color = Color(0xFF1ED292)
            )
        }

        AnimatedVisibility(
            uiState.error != UIErrorType.Nothing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorLayout(uiState.error)
        }

        AnimatedVisibility(
            uiState.notificationDetail != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Back Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.pin),
                        contentDescription = "Pin Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                AndroidView(factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.useWideViewPort = true
                        settings.loadWithOverviewMode = true
                        settings.cacheMode = WebSettings.LOAD_NO_CACHE

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                return handleForDownloadableUrl(request?.url)
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                if (title == "about:blank" && url?.startsWith("file:///android_asset/") == false) {
                                    loadPdfView(url)
                                    return
                                }
                                super.onPageFinished(view, url)
                                loading = false
                            }
                            private fun loadPdfView(url: String) {
                                loadUrl(url)
                                loading = true
                            }

                            private fun handleForDownloadableUrl(uri: Uri?): Boolean {
                                val url = urlEncodeMe(uri)
                                var html = ""

                                if (isBlockedUrl(url)) {
                                    return true
                                }

                                if (isDocPreviewLink(url) || isExcelPreviewLink(url) || isPptPreviewLink(url)) {
                                    settings.useWideViewPort = false
                                    settings.loadWithOverviewMode = false
                                    loadPdfView("${Companion.BASE_URL_FOR_PDF_PREVIEW}" + URLEncoder.encode(url, "ISO-8859-1"))
                                    return true
                                }

                                if (isMovPreviewLink(url)) {
                                    settings.useWideViewPort = false
                                    settings.loadWithOverviewMode = false
                                    html = "<video controls='controls' width='100%' height='200' name=''>\n" +
                                            "<source src='$url'>\n" +
                                            "</video>"
                                }

                                if (html.isNotBlank()) {
                                    html = "<!DOCTYPE><html><body style='width: 100%; height: 100vh; background: black; display: flex; justify-content: center; align-items: center;'>$html</body></html>"
                                    loading = true
                                    loadHtml(html)
                                    return true
                                }

                                return false
                            }
                        }

                        loadUrl(uiState.notificationDetail.renderView)
                    }
                })

                if (loading) {
                    CircularProgressIndicator()
                }

            }
        }
    }

    if (notiType != "") {
        when (notiType) {
            "interview" -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFF1ED292),
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (uiState.notificationDetail != null) {
                            if (uiState.notificationDetail.interviewType == "in_person") {
                                Text(
                                    text = "View on google map",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFFFFFFF),
                                    fontSize = 14.sp,
                                )
                            } else {
                                Text(
                                    text = "Join the meeting",
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

            "offer" -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFF1ED292),
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                if (uiState.notificationDetail != null) {
                                    var item = uiState.notificationDetail
                                    navController.navigate("submit-offer/${item.id}/${item.offerAndInterviewLink}")
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "View attachment",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            "assignment" -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .clickable {
                            if (uiState.notificationDetail != null) {
                                var item = uiState.notificationDetail
                                navController.navigate("submit-assignment/${item.jobId}/${item.referenceId}${item.title}/${item.status}/${item.subDomain}/${item.referenceApplicationId}")
                            }
                        },
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                            .background(
                                color = Color(0xFF1ED292),
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Submit Assignment",
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

class Companion {
    companion object {
        const val BASE_URL_FOR_PDF_PREVIEW =
            "https://docs.google.com/viewerng/viewer?embedded=true&url="
        val BLOCK_URL_PREFIXES = listOf(
            "https://docs.google.com/viewerng/viewer?url="
        )

        val EXCEL_PREVIEW_URL_SUFFIXES = listOf(
            "csv", "csv", "dbf", "dif",
            "mht", "mhtml", "ods", "pdf", "prn", "slk",
            "xla", "xlam",
            "xls", "xlsb", "xlsm", "xlsx", "xlsx",
            "xlt", "xltm", "xltx", "xlw", "xml", "xml", "xps",
        )
        val PPT_PREVIEW_URL_SUFFIXES = listOf(
            "emf", "odp", "pot", "potm", "potx", "ppa", "ppam",
            "pps", "ppsm", "ppsx", "ppt", "pptm", "pptx",
            "pptx", "pptx", "rtf", "thmx", "tif", "wmf",
            "xml", "xps",
        )
        val DOC_PREVIEW_URL_SUFFIXES = listOf("pdf", "docx", "doc")
        val MOV_PREVIEW_URL_SUFFIXES = listOf(
            "mov", "qt",
            "webm",
            "mpg", "mp2", "mpeg", "mpe", "mpv",
            "ogg",
            "mp4", "m4p", "m4v",
            "avi",
            "wmv",
            "flv", "swf",
            "avchd"
        )
    }
}


private fun urlEncodeMe(uri: Uri?): String {
    return uri.toString()
}

private fun isExcelPreviewLink(url: String): Boolean {
    return url.isContainWithThese(Companion.EXCEL_PREVIEW_URL_SUFFIXES)
}

private fun isMovPreviewLink(url: String): Boolean {
    return url.isContainWithThese(Companion.MOV_PREVIEW_URL_SUFFIXES)
}

private fun isBlockedUrl(url: String): Boolean {
    return url.isStartWithThese(Companion.BLOCK_URL_PREFIXES)
}

private fun isDocPreviewLink(url: String): Boolean {
    return url.isContainWithThese(Companion.DOC_PREVIEW_URL_SUFFIXES)
}

private fun isPptPreviewLink(url: String): Boolean {
    return url.isContainWithThese(Companion.PPT_PREVIEW_URL_SUFFIXES)
}

private fun loadHtml(html: String, mimeType: String = "text/html") {
//    webview.clearCache(true)
//    webview?.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "utf-8", null)
}

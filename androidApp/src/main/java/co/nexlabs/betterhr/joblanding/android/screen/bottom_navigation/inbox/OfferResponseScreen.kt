package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.getFileName
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitOfferViewModel
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

var bottomBarVisible: MutableLiveData<Boolean> = MutableLiveData()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OfferResponseScreen(viewModel: SubmitOfferViewModel, navController: NavController, id: String, referenceId: String, status: String, subDomain: String, link: String) {
    var showDialogForSure by remember { mutableStateOf(false) }

    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Draw", "Upload")

    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

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
                    .size(24.dp)
                    .clickable { navController.popBackStack() },
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Employment_Letter.pdf",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = Color(0xFF4A4A4A),
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        loadUrl(link)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.black_line),
            contentDescription = "Line Icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Action Buttons",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W800,
            color = Color(0xFF6A6A6A),
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.sign_box),
            contentDescription = "Sign Box Icon",
            modifier = Modifier
                .fillMaxWidth()
                .height(155.dp)
                .clickable {
                    bottomBarVisible.postValue(true)
                },
            alignment = Alignment.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(bottom = 32.dp)
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                )
                .border(1.dp, Color(0xFFFF8D29), RoundedCornerShape(8.dp))
                .clickable {
                    showDialogForSure = true
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Reject offer",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFFFF8D29),
                fontSize = 14.sp,
            )
        }

        if (showDialogForSure) {
            ConfirmDialog(viewModel, id, status, subDomain, onDismiss = { showDialogForSure = false })
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (bottomBarVisible.value == true) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, top = 6.dp, bottom = 22.dp),
                            text = "Signature",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF4A4A4A),
                            fontSize = 16.sp,
                        )

                        TabRow(
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    color = Color(0xFF1ED292),
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex])
                                )
                            },
                            contentColor = Color(0xFF1ED292),
                            modifier = Modifier
                                .fillMaxWidth(),
                            selectedTabIndex = tabIndex
                        ) {
                            tabs.forEachIndexed { index, title ->
                                Tab(
                                    text = {
                                        Text(
                                            text = title,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            color = if (tabIndex == index) Color(0xFF1ED292) else Color(
                                                0xFF757575
                                            )
                                        )
                                    },
                                    selected = tabIndex == index,
                                    onClick = { tabIndex = index }
                                )
                            }
                        }
                        when (tabIndex) {
                            0 -> DrawScreen(viewModel, id, referenceId, status, subDomain)
                            1 -> UploadScreen(viewModel, id, referenceId, status, subDomain)
                        }

                    }
                },
                sheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Expanded
                ),
                sheetShape = RoundedCornerShape(
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp,
                    topStart = 24.dp,
                    topEnd = 24.dp
                ),
                sheetElevation = 16.dp,
                sheetBackgroundColor = Color.White,
                sheetContentColor = contentColorFor(Color.White),
                modifier = Modifier.fillMaxWidth(),
                scrimColor = Color.Transparent
            ) {

            }
        }
    }
}

@Composable
fun DrawScreen(viewModel: SubmitOfferViewModel, notiId: String, referenceId: String, status: String, subDomain: String) {
    val applicationContext = LocalContext.current

    val dateFormatWithHour = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDateWithHour = dateFormatWithHour.format(Date())

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccessUploadFile) {
        LaunchedEffect(Unit) {
            if (uiState.fileList != null) {
                var attachments = Json.encodeToString(uiState.fileList)
                Log.d("attachments>", attachments)

                if (!attachments.isNullOrBlank()) {
                    scope.launch {
                        viewModel.responseOffer(
                            referenceId,
                            "note",
                            status,
                            currentDateWithHour,
                            attachments,
                            subDomain
                        )
                    }
                }
            }
        }
    }

    if (uiState.isOfferResponseSuccess) {
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.updateNotification(
                    notiId, "complete"
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(502.dp)
                .padding(bottom = 22.dp)
                .background(
                    color = Color(0xFFF0F8FE),
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    1.dp,
                    Color(0xFFF0F8FE),
                    RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(applicationContext)
                    .data(R.drawable.apply_job_success)
                    .decoderFactory { result, options, _ -> ImageDecoderDecoder(result.source, options) }
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
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable {
                    bottomBarVisible.postValue(false)
                }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    text = "Cancel",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp,
                )
            }

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = Color(0xFF1ED292),
                    RoundedCornerShape(8.dp)
                )
                .clickable {
                    //upload draw sign & response offer
                    //check file empty or not
                    scope.launch {
                       /* viewModel.uploadSingleFile(

                        )*/
                    }

                }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
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

@Composable
fun UploadScreen(viewModel: SubmitOfferViewModel, notiId: String, referenceId: String, status: String, subDomain: String) {

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var applicationContext = LocalContext.current.applicationContext

    val dateFormatWithHour = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDateWithHour = dateFormatWithHour.format(Date())

    var uploadFileName by remember { mutableStateOf("") }
    var uploadFile by remember { mutableStateOf<Uri?>(null) }
    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uploadFile = uri
            uploadFileName = getFileName(applicationContext, uri)
        }

    }

    if (uiState.isSuccessUploadFile) {
        LaunchedEffect(Unit) {
            if (uiState.fileList != null) {
                var attachments = Json.encodeToString(uiState.fileList)
                viewModel.responseOffer(
                    referenceId,
                    "note",
                    status,
                    currentDateWithHour,
                    attachments,
                    subDomain
                )
            }
        }
    }

    if (uiState.isOfferResponseSuccess) {
        LaunchedEffect(Unit) {
            viewModel.updateNotification(
                notiId, "complete"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(502.dp)
                .padding(bottom = 22.dp)
                .background(
                    color = Color(0xFFF0F8FE),
                    shape = MaterialTheme.shapes.medium
                )
                .border(
                    1.dp,
                    Color(0xFFF0F8FE),
                    RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (uploadFile != null) {

                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .fillMaxWidth()
                        .height(470.dp)
                        .padding(16.dp)
                        .border(1.dp, Color(0xFFAAAAAA), RoundedCornerShape(0.dp))
                        .clickable {
                            fileChooserLauncher.launch("*/*")
                        },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.attach_file),
                        contentDescription = "Attach File Image",
                        modifier = Modifier
                            .size(width = 114.dp, height = 81.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .fillMaxWidth()
                        .height(470.dp)
                        .padding(16.dp)
                        .border(1.dp, Color(0xFFAAAAAA), RoundedCornerShape(0.dp)),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(14.dp)
                                    .clickable {
                                        uploadFile = null
                                    },
                                alignment = Alignment.CenterEnd
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.assignment_file_logo),
                                contentDescription = "Attach File Icon",
                                modifier = Modifier
                                    .size(
                                        width = 39.08.dp,
                                        height = 48.dp
                                    ),
                                alignment = Alignment.Center
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                maxLines = 2,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                text = uploadFileName,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .width(114.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable {
                    bottomBarVisible.postValue(false)
                }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    text = "Cancel",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp,
                )
            }

            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = Color(0xFF1ED292),
                    RoundedCornerShape(8.dp)
                )
                .clickable {
                    //upload file sign & response offer
                    if (uploadFile != null) {
                        scope.launch {
                            viewModel.uploadSingleFile(uploadFile!!, uploadFileName, "offer", referenceId)
                        }
                    } else {
                        Toast
                            .makeText(
                                applicationContext,
                                "Please upload Signature!",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

                }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
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

@Composable
fun ConfirmDialog(
    viewModel: SubmitOfferViewModel, id: String, status: String, subDomain: String,
    onDismiss: () -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(186.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.reject_offer_icon),
                    contentDescription = "Reject Offer Icon",
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Are you sure to reject the offer?",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFF6A6A6A)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFFAAAAAA)
                        )
                    }

                    TextButton(onClick = {
                        showDialog = true
                    }) {
                        Text(
                            text = "Sure",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFF1ED292)
                        )
                    }
                }

                if (showDialog) {
                    ReasonDialog(viewModel, id, status, subDomain, onDismiss = { showDialog = false })
                }
            }
        }
    }
}

@Composable
fun ReasonDialog(viewModel: SubmitOfferViewModel, id: String, status: String, subDomain: String, onDismiss: () -> Unit) {

    val applicationContext = LocalContext.current.applicationContext

    val dateFormatWithHour = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDateWithHour = dateFormatWithHour.format(Date())

    var reason by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isOfferResponseSuccess) {
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.updateNotification(
                    id, "reject"
                )
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
                .height(318.dp)
                .border(0.dp, color = Color.White, RoundedCornerShape(8.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Please give us a reason",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFF4A4A4A)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Close Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onDismiss
                            }
                    )
                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(158.dp)
                        .border(
                            1.dp,
                            Color(0xFFE4E7ED),
                            RoundedCornerShape(0.dp)
                        )
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        ),
                    value = reason,
                    onValueChange = {
                        reason = it
                    },
                    placeholder = {
                        Text(
                            "Type here",
                            color = Color(0xFFAAAAAA),
                            fontWeight = FontWeight.W400,
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        )
                    },
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.W400,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color(0xFFAAAAAA)
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color(0xFF4A4A4A),
                        backgroundColor = Color.Transparent,
                        cursorColor = Color(0xFF1ED292),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    ),
                )

                Spacer(modifier = Modifier.height(24.dp))

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
                            if (reason.isNullOrBlank()) {
                                Toast
                                    .makeText(
                                        applicationContext,
                                        "Please fill reason!",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            } else {
                                scope.launch {
                                    viewModel.responseOffer(
                                        id,
                                        reason,
                                        status,
                                        currentDateWithHour,
                                        "",
                                        subDomain
                                    )
                                }
                            }
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Send",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFFFFFFFF),
                        fontSize = 14.sp,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


package co.nexlabs.betterhr.joblanding.android.screen.register

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun CompleteProfileScreen(viewModel: CompleteProfileViewModel, navController: NavController) {
    var profilePath by remember { mutableStateOf("") }
    var cvFileName by remember { mutableStateOf("") }
    var coverLetterName by remember { mutableStateOf("") }
    var bottomBarVisible by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    scope.launch {
        viewModel.getCandidateData()
    }

    if (uiState.candidateData != null) {
        name = uiState.candidateData.name
        position = uiState.candidateData.desiredPosition
        phoneNumber = uiState.candidateData.phone
        email = uiState.candidateData.email

        if (uiState.candidateData.profile != null) {
            profilePath = uiState.candidateData.profile.fullPath
        }

        if (uiState.candidateData.cv != null) {
            cvFileName = uiState.candidateData.cv.name
        }

        if (uiState.candidateData.coverLetter != null) {
            coverLetterName = uiState.candidateData.coverLetter.name
        }
    }

    var applicationContext = LocalContext.current.applicationContext
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var fileToUpload: File? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val contentResolver = applicationContext.contentResolver
            val fileName =
                co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.getFileName(
                    applicationContext,
                    it
                )
            val file = File(applicationContext.cacheDir, fileName)
            contentResolver.openInputStream(it)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            fileToUpload = file

            selectedImageUri = uri
            Log.d("imageFile>>", selectedImageUri.toString())
            Log.d("imageFile>>", fileName)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Profile",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setting Icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        //showMenu = true
                    },
                alignment = Alignment.Center
            )
        }

        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (profilePath != "") {
                        val imageRequest = remember(profilePath) {
                            ImageRequest.Builder(applicationContext)
                                .data(profilePath).build()
                        }

                        Image(
                            painter = rememberImagePainter(
                                request = imageRequest,
                            ),
                            contentDescription = "Profile Icon",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .graphicsLayer {
                                    shape = CircleShape
                                },
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Edit Camera Logo",
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape),
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.height(58.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.candidateData.name,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp
                        )

                        Text(
                            text = uiState.candidateData.desiredPosition,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    bottomBarVisible = true
                                    //navController.navigate("profile-edit-detail-screen")
                                },
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "edit profile",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 12.sp
                            )

                            Image(
                                painter = painterResource(id = R.drawable.chevron_right),
                                contentDescription = "Arrow Icon",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = "Phone Icon",
                        modifier = Modifier
                            .size(20.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = uiState.candidateData.phone,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF6A6A6A),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.emil_icon),
                        contentDescription = "Email Icon",
                        modifier = Modifier
                            .size(20.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = uiState.candidateData.email,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF6A6A6A),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(30.dp)
                        .border(1.dp, Color(0xFFFDEDEC), RoundedCornerShape(4.dp))
                        .background(color = Color(0xFFFDEDEC), shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = "Warning Icon",
                            modifier = Modifier
                                .size(13.33.dp),
                            alignment = Alignment.Center
                        )

                        Text(
                            text = " Need Verification. ",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFEE4744),
                            fontSize = 12.sp
                        )

                        Text(
                            modifier = Modifier.drawBehind {
                                val strokeWidthPx = 1.dp.toPx()
                                val verticalOffset = size.height - 2.sp.toPx()
                                drawLine(
                                    color = Color(0xFFEE4744),
                                    strokeWidth = strokeWidthPx,
                                    start = Offset(0f, verticalOffset),
                                    end = Offset(size.width, verticalOffset)
                                )
                            },
                            text = "Click here to verify your email.",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFEE4744),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(52.dp)
                        .border(1.dp, Color(0xFFFEF9E6), RoundedCornerShape(4.dp))
                        .background(color = Color(0xFFFEF9E6), shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alert_circle),
                            contentDescription = "Alert Icon",
                            modifier = Modifier
                                .size(20.dp),
                            alignment = Alignment.Center
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Please complete your profile!",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MultiStyleTextForCompleteProfile(
                        text1 = "Resume or CV ",
                        color1 = Color(0xFF6A6A6A),
                        text2 = "*",
                        color2 = Color(0xFFEE4744)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(200.dp)
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                        .DashBorder(1.dp, Color(0xFF757575), 4.dp),
                ) {

                    if (cvFileName != "") {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
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
                                        .size(14.dp),
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
                                    painter = painterResource(id = R.drawable.pdf_file_icon),
                                    contentDescription = "Attach File Icon",
                                    modifier = Modifier
                                        .size(width = 39.08.dp, height = 48.dp),
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
                                    text = cvFileName,
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
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.attach_file),
                                contentDescription = "Attach File Icon",
                                modifier = Modifier
                                    .size(width = 114.dp, height = 180.dp),
                                alignment = Alignment.Center
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Files Attachments",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF6A6A6A),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                AnimatedVisibility(
                    visible = !coverLetterName.isNullOrBlank(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    FlowRow(
                        maxItemsInEachRow = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(1) { index ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(
                                        color = Color(0xFFF2F6FC),
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .DashBorder(1.dp, Color(0xFFA7BAC5), 4.dp),
                                contentAlignment = Alignment.Center
                            ) {

                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.pdf_file_logo),
                                            contentDescription = "PDF Logo Icon",
                                            modifier = Modifier
                                                .size(24.dp)
                                        )

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Text(
                                                text = coverLetterName,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF4A4A4A),
                                                fontSize = 14.sp,
                                                softWrap = true,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Text(
                                                text = "138 KB",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF757575),
                                                fontSize = 8.sp
                                            )
                                        }
                                    }

                                    Image(
                                        painter = painterResource(id = R.drawable.x),
                                        contentDescription = "X Icon",
                                        modifier = Modifier
                                            .size(16.dp)
                                    )
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(40.dp)
                        .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.upload_icon),
                            contentDescription = "Upload Icon",
                            modifier = Modifier
                                .size(20.dp)
                        )

                        Text(
                            text = "Upload Documents",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF1ED292),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 72.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                /*Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Summary",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Experience",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Education",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Language",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Other Skills",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Certifications",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 72.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )*/

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (bottomBarVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Edit Profile",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF4A4A4A),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        bottomBarVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Column(
                            modifier = Modifier
                                .size(68.dp)
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Overlap(
                                modifier = Modifier.wrapContentSize()
                            ) {
                                if (selectedImageUri != null) {

                                    /*scope.launch {
                                        selectedImageUri?.let { uri ->
                                            viewModel.uploadFile(uri, "profile")
                                        }
                                    }*/

                                    val imageRequest = remember(selectedImageUri) {
                                        ImageRequest.Builder(applicationContext)
                                            .data(selectedImageUri).build()
                                    }

                                    Image(
                                        painter = rememberImagePainter(
                                            request = imageRequest,
                                        ),
                                        contentDescription = "Profile Icon",
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(CircleShape)
                                            .graphicsLayer {
                                                shape = CircleShape
                                            },
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    if (uiState.candidateData != null) {
                                        if (uiState.candidateData.profilePath != null) {
                                            Image(
                                                painter = rememberGlidePainter(
                                                    request = uiState.candidateData.profilePath
                                                ),
                                                contentDescription = "Profile Icon",
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape)
                                                    .graphicsLayer {
                                                        shape = CircleShape
                                                    },
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.camera),
                                                contentDescription = "Profile Icon",
                                                modifier = Modifier
                                                    .size(64.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Fit
                                            )
                                        }
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.camera),
                                            contentDescription = "Profile Icon",
                                            modifier = Modifier
                                                .size(64.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }

                                Image(
                                    painter = painterResource(id = R.drawable.edit_camera),
                                    contentDescription = "Edit Camera Logo",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape),
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "Name",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            placeholder = { Text(name, color = Color(0xFF4A4A4A)) },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                color = Color(0xFF4A4A4A)
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
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Icon",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            },
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "Position Description",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = position,
                            onValueChange = {
                                position = it
                            },
                            placeholder = { Text(position, color = Color(0xFF4A4A4A)) },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                color = Color(0xFF4A4A4A)
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
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Icon",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            },
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "Phone number",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = phoneNumber,
                            onValueChange = {
                                phoneNumber = it
                            },
                            placeholder = { Text(phoneNumber, color = Color(0xFF4A4A4A)) },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                color = Color(0xFF4A4A4A)
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color(0xFF4A4A4A),
                                backgroundColor = Color.Transparent,
                                cursorColor = Color(0xFF1ED292),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Icon",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            },
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Start,
                            text = "Email",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            placeholder = { Text(email, color = Color(0xFF4A4A4A)) },
                            textStyle = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                color = Color(0xFF4A4A4A)
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color(0xFF4A4A4A),
                                backgroundColor = Color.Transparent,
                                cursorColor = Color(0xFF1ED292),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                            singleLine = true,
                            trailingIcon = {
                                Image(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = "Edit Icon",
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            },
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.warning),
                                contentDescription = "Warning Icon",
                                modifier = Modifier
                                    .size(13.33.dp)
                            )

                            Text(
                                modifier = Modifier.drawBehind {
                                    val strokeWidthPx = 1.dp.toPx()
                                    val verticalOffset = size.height - 2.sp.toPx()
                                    drawLine(
                                        color = Color(0xFFEE4744),
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, verticalOffset),
                                        end = Offset(size.width, verticalOffset)
                                    )
                                },
                                textAlign = TextAlign.Start,
                                text = "Verify Email",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFFEE4744),
                                fontSize = 12.sp
                            )
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
fun MultiStyleTextForCompleteProfile(text1: String, color1: Color, text2: String, color2: Color) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = color1,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
            ) {
                append(text1)
            }
            withStyle(
                style = SpanStyle(
                    color = color2,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
            ) {
                append(text2)
            }
        }
    )
}

@Composable
fun Overlap(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + smallPlaceable.height / 2,
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = 115,
                y = largePlaceable.height - smallPlaceable.height
            )
        }
    }
}
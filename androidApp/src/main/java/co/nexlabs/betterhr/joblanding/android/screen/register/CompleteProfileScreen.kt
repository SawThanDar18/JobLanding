package co.nexlabs.betterhr.joblanding.android.screen.register

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.AndroidFileUri
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.ExperienceUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.FilesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.PositionUIModel
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun CompleteProfileScreen(viewModel: CompleteProfileViewModel, navController: NavController) {
    var refreshing by remember { mutableStateOf(false) }

    var companyName by remember { mutableStateOf("") }

    var profilePath by remember { mutableStateOf("") }
    var cvFileName by remember { mutableStateOf("") }
    var coverLetterName by remember { mutableStateOf("") }
    var bottomBarVisible by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var candidatePosition by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }
    var companiesList: MutableList<CompaniesUIModel> = ArrayList()
    var experienceList: MutableList<ExperienceUIModel> = ArrayList()
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var addSummaryVisible by remember { mutableStateOf(false) }
    var addExperienceCompanyVisible by remember { mutableStateOf(false) }
    var addExperiencePositionVisible by remember { mutableStateOf(false) }
    var updateExperiencePositionVisible by remember { mutableStateOf(false) }
    var addEducationVisible by remember { mutableStateOf(false) }
    var addLanguageVisible by remember { mutableStateOf(false) }
    var addSkillVisible by remember { mutableStateOf(false) }
    var addCertificationVisible by remember { mutableStateOf(false) }

    var companyIdForAddExperience by remember { mutableStateOf("") }

    var position by remember { mutableStateOf("") }
    var experienceLevel by remember { mutableStateOf("") }

    val jobTypes = listOf("Full-time", "Part-time", "Internship", "Remote")
    var selectedJobType by remember { mutableStateOf(jobTypes[0]) }

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var startDateInYMD by remember { mutableStateOf("") }
    var endDateInYMD by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            scope.launch {
                viewModel.getCandidateData()
            }
            refreshing = false
        }
    }

    scope.launch {
        viewModel.getCandidateData()
    }

    LaunchedEffect(uiState.isSuccessUpdateSummary) {
        addSummaryVisible = false
        scope.launch {
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.getPositionId) {
        if (uiState.positionId != "") {
            viewModel.createExperience(
                uiState.positionId,
                companyIdForAddExperience,
                position,
                "",
                experienceLevel,
                selectedJobType,
                startDateInYMD, endDateInYMD,
                if (endDate == "") true else false,
                description
            )
        }
    }

    LaunchedEffect(uiState.isSuccessCreateExperience) {
        addExperiencePositionVisible = false
    }

    LaunchedEffect(uiState.isSuccessUpdateExperience) {
        updateExperiencePositionVisible = false
    }

    LaunchedEffect(uiState.getFileId) {
        if (bottomBarVisible) {
            bottomBarVisible = false
        }

        if (addExperienceCompanyVisible) {
            if (uiState.fileId != "") {
                scope.launch {
                    viewModel.createCompany(companyName, uiState.fileId)
                }
            }
        }
    }

    LaunchedEffect(uiState.isSuccessCreateCompany) {
        addExperienceCompanyVisible = false
    }

    if (uiState.candidateData != null) {
        scope.launch {
            viewModel.updateCandidateId(uiState.candidateData.id)
            viewModel.updatePhone(uiState.candidateData.phone)
        }
        name = uiState.candidateData.name
        candidatePosition = uiState.candidateData.desiredPosition
        phoneNumber = uiState.candidateData.phone
        email = uiState.candidateData.email
        summary = uiState.candidateData.summary

        if (uiState.candidateData.companies.isNotEmpty()) {
            uiState.candidateData.companies.map {
                companiesList.add(
                    CompaniesUIModel(
                        it.id,
                        it.name,
                        FilesUIModel(it.file.id, it.file.name, it.file.type, it.file.fullPath),
                        it.experience
                    )
                )

                /*if (it.experience.isNotEmpty()) {
                    it.experience.map {
                        experienceList.add(
                            ExperienceUIModel(
                                it.id, it.positionId, it.candidateId,
                                it.title, it.location, it.experienceLevel,
                                it.employmentType, it.startDate, it.endDate,
                                it.isCurrentJob, it.description, it.companyId,
                                PositionUIModel(it.position.id, it.position.name)
                            )
                        )
                    }
                }*/
            }
        }

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
    var selectedFileName by remember { mutableStateOf("") }
    var fileToUpload: File? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val contentResolver = applicationContext.contentResolver
            selectedFileName =
                co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.getFileName(
                    applicationContext,
                    it
                )
            val file = File(applicationContext.cacheDir, selectedFileName)
            contentResolver.openInputStream(it)?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            fileToUpload = file

            selectedImageUri = uri
        }
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = refreshing),
        onRefresh = { refreshing = true },
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            AnimatedVisibility(
                uiState.error != UIErrorType.Nothing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                ErrorLayout(uiState.error)
            }

            AnimatedVisibility(
                (uiState.candidateData != null),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 80.dp),
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
                                    navController.navigate("setting-screen")
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
                                    .background(
                                        color = Color(0xFFFDEDEC),
                                        shape = MaterialTheme.shapes.medium
                                    ),
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
                                    .background(
                                        color = Color(0xFFFEF9E6),
                                        shape = MaterialTheme.shapes.medium
                                    ),
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
                                    .background(
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    )
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

                                                    Column(
                                                        verticalArrangement = Arrangement.spacedBy(
                                                            4.dp
                                                        )
                                                    ) {
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
                                    .background(
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
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
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                            if ((summary == "")) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(72.dp)
                                        .padding(horizontal = 16.dp),
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
                                        painter = painterResource(id = R.drawable.upload_icon),
                                        contentDescription = "Plus Icon",
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clickable {
                                                addSummaryVisible = true
                                            },
                                    )

                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(72.dp)
                                            .padding(horizontal = 16.dp),
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
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = "Edit Icon",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    addSummaryVisible = true
                                                },
                                        )

                                    }

                                    Text(
                                        text = summary,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF4A4A4A),
                                        fontSize = 14.sp,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                            if (companiesList.isEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(72.dp)
                                        .padding(horizontal = 16.dp),
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
                                            .size(24.dp)
                                            .clickable {
                                                addExperienceCompanyVisible = true
                                            },
                                        alignment = Alignment.Center
                                    )
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(72.dp)
                                            .padding(horizontal = 16.dp),
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
                                                .size(24.dp)
                                                .clickable {
                                                    addExperienceCompanyVisible = true
                                                },
                                            alignment = Alignment.Center
                                        )

                                    }

                                    FlowRow(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .fillMaxWidth(),
                                        maxItemsInEachRow = 1,
                                        horizontalArrangement = Arrangement.Start,
                                    ) {

                                        var lastItem by remember { mutableStateOf(0) }
                                        lastItem = companiesList.size

                                        repeat(companiesList.size) { index ->
                                            var company = companiesList[index]

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                            ) {
                                                if (company.file.fullPath != "") {
                                                    val imageRequest =
                                                        remember(company.file.fullPath) {
                                                            ImageRequest.Builder(applicationContext)
                                                                .data(company.file.fullPath).build()
                                                        }

                                                    Image(
                                                        painter = rememberImagePainter(
                                                            request = imageRequest,
                                                        ),
                                                        contentDescription = "Company Logo Icon",
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape)
                                                            .graphicsLayer {
                                                                shape = CircleShape
                                                            },
                                                        contentScale = ContentScale.Crop
                                                    )
                                                } else {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.add_company_logo),
                                                        contentDescription = "Profile Icon",
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape)
                                                            .graphicsLayer {
                                                                shape = CircleShape
                                                            },
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }

                                                Column(
                                                    horizontalAlignment = Alignment.Start,
                                                ) {
                                                    Text(
                                                        text = company.name,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 14.sp,
                                                    )

                                                    if (company.experience.isEmpty()) {
                                                        Text(
                                                            text = "--",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W500,
                                                            color = Color(0xFF757575),
                                                            fontSize = 12.sp,
                                                        )
                                                    } else {

                                                        experienceList.clear()
                                                        company.experience.map {
                                                            experienceList.add(
                                                                ExperienceUIModel(
                                                                    it.id,
                                                                    it.positionId,
                                                                    it.candidateId,
                                                                    it.title,
                                                                    it.location,
                                                                    it.experienceLevel,
                                                                    it.employmentType,
                                                                    it.startDate,
                                                                    it.endDate,
                                                                    it.isCurrentJob,
                                                                    it.description,
                                                                    it.companyId,
                                                                    PositionUIModel(
                                                                        it.position.id,
                                                                        it.position.name
                                                                    )
                                                                )
                                                            )
                                                        }

                                                        FlowRow(
                                                            maxItemsInEachRow = 1,
                                                            verticalArrangement = Arrangement.spacedBy(
                                                                16.dp
                                                            )
                                                        ) {
                                                            repeat(experienceList.size) { index ->
                                                                var experience =
                                                                    experienceList[index]

                                                                var years by remember {
                                                                    mutableStateOf(
                                                                        0
                                                                    )
                                                                }
                                                                var months by remember {
                                                                    mutableStateOf(
                                                                        0
                                                                    )
                                                                }

                                                                if (experience.startDate == "0000-00-00 00:00:00" && experience.endDate == "0000-00-00 00:00:00") {
                                                                    years = 0
                                                                    months = 0
                                                                } else if (experience.startDate == "0000-00-00 00:00:00" && experience.endDate == "") {
                                                                    years = 0
                                                                    months = 0
                                                                } else if (experience.startDate != "0000-00-00 00:00:00" && experience.endDate != "0000-00-00 00:00:00"){
                                                                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                                                                    val startDate = sdf.parse(experience.startDate)
                                                                    val endDate = sdf.parse(experience.endDate)

                                                                    calculateDateDifference(
                                                                        startDate,
                                                                        endDate
                                                                    ) { calculatedYears, calculatedMonths ->
                                                                        years = calculatedYears
                                                                        months = calculatedMonths
                                                                    }
                                                                } else if ((experience.startDate != "0000-00-00 00:00:00" && experience.endDate == "")) {
                                                                    years = 1
                                                                    months = 1
                                                                }

                                                                var dateDiff = if (years != 0 && months == 0) {
                                                                    "$years yr"
                                                                } else if (months != 0 && years == 0) {
                                                                    "$months mo"
                                                                } else if (years != 0 && months != 0) {
                                                                    "$years yr $months mo"
                                                                } else if (years == 1 && months == 1) {
                                                                    "current working"
                                                                } else {
                                                                    "--"
                                                                }

                                                                Column() {
                                                                    Text(
                                                                        text = dateDiff,
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
                                                                        fontWeight = FontWeight.W500,
                                                                        color = Color(0xFF757575),
                                                                        fontSize = 12.sp,
                                                                    )

                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            8.dp
                                                                        )
                                                                    )
                                                                }

                                                            }
                                                        }
                                                    }

                                                    Text(
                                                        text = "+ Add Position",
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF42A5F5),
                                                        fontSize = 12.sp,
                                                        modifier = Modifier
                                                            .padding(
                                                                top = 8.dp, bottom = 16.dp
                                                            )
                                                            .clickable {
                                                                addExperiencePositionVisible = true
                                                                companyIdForAddExperience =
                                                                    company.id
                                                            }
                                                    )

                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        addEducationVisible = true
                                    },
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        addLanguageVisible = true
                                    },
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        addSkillVisible = true
                                    },
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp)
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        addCertificationVisible = true
                                    },
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
                                    .fillMaxWidth()
                                    .padding(bottom = 72.dp)
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                        }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (addExperiencePositionVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Let’s add your experience",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF4A4A4A),
                                fontSize = 16.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        addExperiencePositionVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Position",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFE4E7ED),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = position,
                                    onValueChange = {
                                        position = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter Position",
                                            color = Color(0xFFBDBDBD),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        )
                                    },
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Start,
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
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            keyboardController?.hide()
                                        }
                                    ),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Experience Level",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFE4E7ED),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = experienceLevel,
                                    onValueChange = {
                                        experienceLevel = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter Level",
                                            color = Color(0xFFBDBDBD),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        )
                                    },
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Start,
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
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            keyboardController?.hide()
                                        }
                                    ),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Job Type",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    items(jobTypes.size) { index ->
                                        var jobType = jobTypes[index]

                                        OutlinedButton(
                                            onClick = { selectedJobType = jobType },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                backgroundColor = Color.Transparent,
                                                contentColor = if (selectedJobType == jobType) Color(
                                                    0xFF1ED292
                                                ) else Color(0xFFE1E1E1)
                                            ),
                                            border = if (selectedJobType == jobType) {
                                                BorderStroke(2.dp, Color(0xFF1ED292))
                                            } else {
                                                BorderStroke(1.dp, Color(0xFFE1E1E1))
                                            },
                                            shape = RoundedCornerShape(4.dp),
                                            modifier = Modifier.height(29.dp)
                                        ) {
                                            Text(
                                                text = jobType,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.W400,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = if (selectedJobType == jobType) Color(
                                                    0xFF1ED292
                                                ) else Color(0xFF757575),
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Enter year",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    DateButton(
                                        LocalContext.current,
                                        "Start Date",
                                        startDate,
                                        onDateSelected = { date ->
                                            startDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            startDateInYMD = dateInYMD
                                        })

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = "To",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W400,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFF757575),
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    DateButton(
                                        LocalContext.current,
                                        "End Date",
                                        endDate,
                                        onDateSelected = { date ->
                                            endDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            endDateInYMD = dateInYMD
                                        })
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Description",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(158.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFE4E7ED),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = description,
                                    onValueChange = {
                                        description = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter Description",
                                            color = Color(0xFFBDBDBD),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        )
                                    },
                                    textStyle = TextStyle(
                                        textAlign = TextAlign.Start,
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
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            keyboardController?.hide()
                                        }
                                    ),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(bottom = 72.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFF1ED292),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .background(
                                                color = Color(0xFF1ED292),
                                                shape = MaterialTheme.shapes.medium
                                            )
                                            .clickable {
                                                var validate =
                                                    (position != "" && experienceLevel != "" && selectedJobType != "" && startDate != "")
                                                if (validate) {
                                                    scope.launch {
                                                        viewModel.createPosition(position)
                                                    }
                                                } else {
                                                    if (position == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Position!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (experienceLevel == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Experience Level!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (selectedJobType == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Job Type!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (startDate == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please select Start Date!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Confirm",
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {
        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
        var imageFileName by remember { mutableStateOf("") }
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                imageFileName = getFileName(applicationContext, it)
                selectedImageUri = uri
            }
        }

        if (addExperienceCompanyVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Create Company",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF4A4A4A),
                                fontSize = 16.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        addExperienceCompanyVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Text(
                            text = "Enter company name",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .border(
                                    1.dp,
                                    Color(0xFFE4E7ED),
                                    RoundedCornerShape(4.dp)
                                )
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = companyName,
                            onValueChange = {
                                companyName = it
                            },
                            placeholder = {
                                Text(
                                    "Type Company",
                                    color = Color(0xFFAAAAAA),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )
                            },
                            textStyle = TextStyle(
                                textAlign = TextAlign.Start,
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
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Company Logo",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (selectedImageUri != null) {
                            val imageRequest = remember(selectedImageUri) {
                                ImageRequest.Builder(applicationContext)
                                    .data(selectedImageUri).build()
                            }
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        request = imageRequest,
                                    ),
                                    contentDescription = "Add Company Logo",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            launcher.launch("image/*")
                                        },
                                    contentScale = ContentScale.Crop
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .padding(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.close_with_bg),
                                        contentDescription = "Close Logo",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                selectedImageUri = null
                                            },
                                    )
                                }
                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.add_company_logo),
                                contentDescription = "Add Company Logo",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clickable {
                                        launcher.launch("image/*")
                                    },
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 72.dp)
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
                                        if (companyName == "") {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Please add Company name!",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else {
                                            scope.launch {
                                                if (selectedImageUri != null) {
                                                    selectedImageUri?.let { uri ->
                                                        val fileUri = AndroidFileUri(uri)
                                                        viewModel.uploadFile(
                                                            fileUri,
                                                            imageFileName,
                                                            "company_logo"
                                                        )
                                                    }
                                                } else {
                                                    viewModel.createCompany(companyName, "")
                                                }
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Create",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFFFFFFF),
                                    fontSize = 14.sp,
                                )
                            }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {
        var summaryUpdate by remember { mutableStateOf("") }
        if (summary != "") {
            summaryUpdate = summary
        }

        if (addSummaryVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Let’s add your summary",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF4A4A4A),
                                fontSize = 16.sp,
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        addSummaryVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        MultiStyleTextForInfo(
                            text1 = "Indicates required",
                            color1 = Color(0xFFAAAAAA),
                            text2 = "*",
                            color2 = Color(0xFFffa558)
                        )

                        Text(
                            text = "Tell employer about yourself",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(158.dp)
                                .border(
                                    1.dp,
                                    Color(0xFFE4E7ED),
                                    RoundedCornerShape(4.dp)
                                )
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = summaryUpdate,
                            onValueChange = {
                                summaryUpdate = it
                            },
                            placeholder = {
                                Text(
                                    "Type Something",
                                    color = Color(0xFFAAAAAA),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )
                            },
                            textStyle = TextStyle(
                                textAlign = TextAlign.Start,
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
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            ),
                        )

                        Row(
                            verticalAlignment = Alignment.Bottom,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 72.dp)
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
                                        if (!summaryUpdate.isNullOrBlank()) {
                                            scope.launch {
                                                viewModel.updateSummary(summaryUpdate)
                                            }
                                        } else {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Please add Summary!",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "Update summary",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFFFFFFFF),
                                    fontSize = 14.sp,
                                )
                            }
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
                                        if (uiState.candidateData != null) {
                                            if (uiState.candidateData.profile != null) {
                                                selectedImageUri?.let { uri ->
                                                    val fileUri = AndroidFileUri(uri)
                                                    viewModel.updateFile(
                                                        fileUri,
                                                        selectedFileName,
                                                        uiState.candidateData.profile.type,
                                                        uiState.candidateData.profile.id,
                                                    )
                                                }
                                            } else {
                                                selectedImageUri?.let { uri ->
                                                    val fileUri = AndroidFileUri(uri)
                                                    viewModel.uploadFile(
                                                        fileUri,
                                                        selectedFileName,
                                                        "profile"
                                                    )
                                                }
                                            }
                                        }
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
fun MultiStyleTextForAddExperience(text1: String, color1: Color, text2: String, color2: Color) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = color1,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp
                )
            ) {
                append(text1)
            }
            withStyle(
                style = SpanStyle(
                    color = color2,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp
                )
            ) {
                append(text2)
            }
        }
    )
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
fun MultiStyleTextForInfo(text1: String, color1: Color, text2: String, color2: Color) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = color1,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
                )
            ) {
                append(text1)
            }
            withStyle(
                style = SpanStyle(
                    color = color2,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp
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

@Composable
fun DateButton(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            showDatePicker = true
        },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .height(40.dp)
            .background(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                modifier = Modifier
                    .size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = if (date.isEmpty()) label else date,
                color = if (date.isEmpty()) Color(0xFFBDBDBD) else Color(0xFF4A4A4A),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
        }
    }

    if (showDatePicker) {
        showDatePickerDialog(
            context = context,
            onDateSelected = { selectedDate ->
                onDateSelected(selectedDate)
                showDatePicker = false
            },
            onDateSelectedInYMD = { selectedDateInYMD ->
                onDateSelectedInYMD(selectedDateInYMD)
                showDatePicker = false
            },
            onDismissRequest = { showDatePicker = false }
        )
    }
}

@Composable
fun showDatePickerDialog(
    context: Context,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    LaunchedEffect(Unit) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }
                val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedCalendar.time)
                onDateSelected(formattedDate)

                val dateFormatInYMD = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDateInYMD = dateFormatInYMD.format(selectedCalendar.time)
                onDateSelectedInYMD(formattedDateInYMD)
            },
            year,
            month,
            day
        )
        datePickerDialog.setOnDismissListener { onDismissRequest() }
        datePickerDialog.show()
    }
}

fun calculateDateDifference(
    startDateInput: Date,
    endDateInput: Date,
    callback: (Int, Int) -> Unit
) {
    val startCalendar = Calendar.getInstance()
    startCalendar.time = startDateInput

    val endCalendar = Calendar.getInstance()
    endCalendar.time = endDateInput

    var years = endCalendar[Calendar.YEAR] - startCalendar[Calendar.YEAR]
    var months = endCalendar[Calendar.MONTH] - startCalendar[Calendar.MONTH]

    if (months < 0) {
        years--
        months += 12
    }

    callback(years, months)

}
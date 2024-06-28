package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.core.net.toFile
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Lifecycle
import co.nexlabs.betterhr.joblanding.AndroidFileUri
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.android.data.CurrentDateTimeFormatted
import co.nexlabs.betterhr.joblanding.android.data.convertDate
import co.nexlabs.betterhr.joblanding.android.screen.register.MultiStyleText
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.home.JobDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobsUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestOptions
import com.google.accompanist.glide.rememberGlidePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalLayoutApi::class, ExperimentalLayoutApi::class,
    ExperimentalLayoutApi::class
)
@Composable
fun JobDetailsScreen(viewModel: JobDetailViewModel, navController: NavController, jobId: String) {

    var isJobSaved by remember { mutableStateOf(false) }
    var isJobApplied by remember { mutableStateOf(false) }

    val currentDateTime = remember { Calendar.getInstance().time }
    val formattedDateTime = remember {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(currentDateTime)
    }

    val items = (0..4).toList()
    var monthList = listOf(
        "January", "February", "March", "April",
        "May", "June", "July", "August",
        "September", "October", "November", "December"
    )
    var selectedItemMonth by remember { mutableStateOf("Start Month") }
    var workingSince by remember { mutableStateOf("") }

    var bottomBarVisibleAfterSignUp by remember { mutableStateOf(false) }
    var bottomBarVisible by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    var isJobSaveSuccessVisible by remember { mutableStateOf(false) }

    var applicationContext = LocalContext.current.applicationContext
    var monthDropDownExpanded by remember { mutableStateOf(false) }
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var startMonth by remember { mutableStateOf("") }
    var startYear by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    var step by remember { mutableStateOf("stepOne") }

    val stepOneAfterSignUp = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = false, isInState = true),
        ApplyJobStepData("Step 3", isCompleted = false, isInState = false),
    )

    val stepTwoAfterSignUp = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 3", isCompleted = false, isInState = true),
    )

    val stepThreeAfterSignUp = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 3", isCompleted = true, isInState = false),
    )

    val stepOne = listOf(
        ApplyJobStepData("Step 1", isCompleted = false, isInState = true),
        ApplyJobStepData("Step 2", isCompleted = false, isInState = false),
        ApplyJobStepData("Step 3", isCompleted = false, isInState = false),
        ApplyJobStepData("Step 4", isCompleted = false, isInState = false),
    )

    val stepTwo = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = false, isInState = true),
        ApplyJobStepData("Step 3", isCompleted = false, isInState = false),
        ApplyJobStepData("Step 4", isCompleted = false, isInState = false),
    )

    val stepThree = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 3", isCompleted = false, isInState = true),
        ApplyJobStepData("Step 4", isCompleted = false, isInState = false),
    )

    val stepFour = listOf(
        ApplyJobStepData("Step 1", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 2", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 3", isCompleted = true, isInState = false),
        ApplyJobStepData("Step 4", isCompleted = false, isInState = true),
    )

    var timer by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

//    val uiStateRegister = viewModel.uiStateRegister.collectAsState(initial = UiState.Loading)
//    val uiStateForVerify = viewModel.uiStateForVerify.collectAsState(initial = UiState.Loading)

    var text by remember { mutableStateOf("") }
    var boxColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }
    var code: List<Char> by remember { mutableStateOf(listOf()) }

    var isClearCV by remember { mutableStateOf(false) }
    var isClearCoverLetter by remember { mutableStateOf(false) }

    var fetchSaveJobId by remember { mutableStateOf("") }

    var focusRequesters = remember {
        List(6) { FocusRequester() }
    }

    var timerText by remember { mutableStateOf("") }

    if (isTimerRunning) {

        LaunchedEffect(Unit) {
            for (i in 59 downTo 0) {
                timer = i
                delay(1000)
            }
        }
    }

    LaunchedEffect(uiState.isGetRequestOTPValue) {
        if (uiState.getRequestOTPValue != "") {
            isTimerRunning = true
        }
    }

    /*when (val currentState = uiStateRegister.value) {
        is UiState.Loading -> {

        }

        is UiState.Success -> {
            isTimerRunning = true
        }

        is UiState.Error -> {
            MyToast(currentState.errorMessage)
        }
    }*/

    if (timer == 0) {
        isTimerRunning = false
    } else {
        timerText = timer.toString()
    }

    LaunchedEffect(uiState.isGetVerifyOTPValue) {
        if (uiState.getVerifyOTPValue != "") {
            scope.launch {
                viewModel.updateToken(uiState.getVerifyOTPValue)
            }
            step = "stepTwo"
        }
    }

    /*when (val currentState = uiStateForVerify.value) {
        is UiState.Loading -> {
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.updateToken(currentState.data)
                }
                step = "stepTwo"
            }
        }

        is UiState.Error -> {
            MyToast(currentState.errorMessage)
        }
    }*/

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {
                Log.d("state>>", "destroyed")
            }
            Lifecycle.State.INITIALIZED -> {
                Log.d("state>>", "initialized")
            }
            Lifecycle.State.CREATED -> {
                scope.launch {
                    if (viewModel.getBearerToken() != "") {
                        viewModel.getCandidateData()
                    }
                    viewModel.getJobDetail(jobId)
                }
                Log.d("state>>", "created")
            }
            Lifecycle.State.STARTED -> {
                scope.launch {
                    if (viewModel.getBearerToken() != "") {
                        viewModel.getCandidateData()
                    }
                    viewModel.getJobDetail(jobId)
                }
                Log.d("state>>", "started")
            }
            Lifecycle.State.RESUMED -> {
                scope.launch {
                    if (viewModel.getBearerToken() != "") {
                        viewModel.getCandidateData()
                    }
                    viewModel.getJobDetail(jobId)
                }
                Log.d("state>>", "resume")
            }
        }
    }

    LaunchedEffect (uiState.isSuccessForCandidateId) {
        if (uiState.isSuccessForCandidateId) {
            scope.launch {
                viewModel.updateCandidateId(uiState.candidateId)
                viewModel.getBearerTokenFromAPI(viewModel.getToken())
            }
        }
    }

    var isSaveItem by remember { mutableStateOf(false) }

    LaunchedEffect (uiState.isSuccessGetJobDetail) {
        if (uiState.isSuccessGetJobDetail) {
            scope.launch {
                if (viewModel.getBearerToken() != "") {
                    viewModel.fetchSaveJobsById(jobId)
                    viewModel.checkJobIsApplied(jobId)
                }
            }
        }
    }

    if (uiState.fetchSaveJobs.data!!.id != "") {
        isSaveItem = true
        fetchSaveJobId = uiState.fetchSaveJobs.data!!.id
    } else {
        isSaveItem = false
    }

    LaunchedEffect (uiState.isUnSaveJobSuccess) {
        if (uiState.isUnSaveJobSuccess) {
            isSaveItem = false
            viewModel.fetchSaveJobsById(jobId)
        }
    }

    LaunchedEffect (uiState.isSaveJobSuccess) {
        if (uiState.isSaveJobSuccess) {
            isSaveItem = true
            viewModel.fetchSaveJobsById(jobId)
        }
    }

    if (uiState.candidateData != null) {
        scope.launch {
            viewModel.updateCandidateId(uiState.candidateData.id)
        }
    }

    /* if (uiState.isBearerTokenExist) {
         LaunchedEffect(Unit) {
             scope.launch {
                 viewModel.fetchSaveJobsById(jobId)
             }
         }
     }*/

    /*LaunchedEffect(isJobSaveSuccessVisible) {
        scope.launch {
            delay(2000)
            isJobSaveSuccessVisible = false
        }
    }

    LaunchedEffect(isSuccessSaveJob) {
        delay(3000)
        uiState.isSaveJobSuccess = false
    }*/

    /*if (uiState.candidateData != null) {
        fullName = uiState.candidateData.name
        email = uiState.candidateData.email
    }*/


    LaunchedEffect (uiState.isSuccessCreateApplication) {
        if (uiState.isSuccessCreateApplication) {
            isJobApplied = true
            step = if (bottomBarVisibleAfterSignUp) {
                "stepThree"
            } else {
                "stepFour"
            }
            /*if (uiState.idFromCreateApplication != "" && uiState.jobDetail.id != "" && uiState.jobDetail.company.subDomain != "") {
                scope.launch {
                    viewModel.applyJob(
                        uiState.idFromCreateApplication,
                        uiState.jobDetail.id,
                        uiState.jobDetail.company.subDomain
                    )
                }
            }*/
        }
        /* LaunchedEffect(Unit) {
             if (uiState.idFromCreateApplication != "") {
                 scope.launch {
                     viewModel.updateApplication(uiState.idFromCreateApplication)
                 }
             }
         }*/
    }

    /* if (uiState.isSuccessUpdateApplication) {
         LaunchedEffect(Unit) {
             if (uiState.idFromCreateApplication != "" && uiState.jobDetail.id != "" && uiState.jobDetail.company.subDomain != "") {
                 scope.launch {
                     viewModel.applyJob(
                         uiState.idFromCreateApplication,
                         uiState.jobDetail.id,
                         uiState.jobDetail.company.subDomain
                     )
                 }
             }
         }
     }*/

    LaunchedEffect (uiState.isApplyJobSuccess) {
        if (uiState.isApplyJobSuccess) {
            step = if (bottomBarVisibleAfterSignUp) {
                "stepThree"
            } else {
                "stepFour"
            }
        }
    }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageFileName by remember { mutableStateOf("") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageFileName = getFileName(applicationContext, it)
            selectedImageUri = uri
            Log.d("imageFile>>", selectedImageUri.toString())
            Log.d("imageFile>>Name", imageFileName)
        }
    }

    var cvFileName by remember { mutableStateOf("") }
    var cvFile by remember { mutableStateOf<Uri?>(null) }

    var coverLetterFileList = remember { mutableStateListOf<FileInfo>() }
    val fileListChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            coverLetterFileList.add(
                FileInfo(
                    "cover_letter",
                    uri,
                    getFileName(applicationContext, uri),
                    getFileSize(applicationContext, uri)
                )
            )
        }

    }

    //var coverLetterFile = remember { mutableStateListOf<FileInfo>() }
    /*var coverLetterFileList by remember { mutableStateOf<List<Uri>?>(emptyList()) }
    val fileListChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri>? ->
            coverLetterFileList = uris?.mapNotNull { uri ->
                uri
            } ?: emptyList()
            coverLetterFile = uris?.mapNotNull { uri ->
                if (applicationContext.contentResolver.getType(uri) == "application/pdf") {
                    FileInfo(
                        "cover_letter",
                        uri,
                        getFileName(applicationContext, uri),
                        getFileSize(applicationContext, uri)
                    )
                } else {
                    null
                }
            } ?: emptyList()
        }
    )*/

    val fileChooserLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            cvFile = it
            cvFileName = getFileName(applicationContext, it)
            Log.d("fileName>>", cvFileName)
            Log.d("file>>", cvFile.toString())
        }
    }

    LaunchedEffect (uiState.isSuccessForBearerToken) {
        if (uiState.isSuccessForBearerToken) {
            scope.launch {
                viewModel.updateBearerToken(uiState.bearerToken)
                selectedImageUri?.let { uri ->
                    val fileUri = AndroidFileUri(uri)
                    viewModel.uploadFile(
                        fileUri,
                        imageFileName,
                        "profile"
                    )
                }
                step = "stepThree"
            }
        }
    }


    /*var fileIds: MutableList<String> = ArrayList()
    if (uiState.isSuccessUploadMultipleFile) {
        if (uiState.multiFileList.isNotEmpty()) {
            uiState.multiFileList.map {
                fileIds.add(it.id)
            }
        }
    }

    if (fileIds.isNotEmpty()) {
        scope.launch {
            viewModel.createApplication(
                uiState.jobDetail.id,
                uiState.jobDetail.company.subDomain,
                uiState.jobDetail.position,
                formattedDateTime,
                jobTitle,
                companyName,
                convertDate("$selectedItemMonth $startYear"),
                fileIds
            )
        }
    }*/

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Boxes(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp),
                        model = uiState.jobDetail.company.coverImage,
                        contentDescription = "Cover",
                        contentScale = ContentScale.Crop,
                    )

                    AsyncImage(
                        modifier = Modifier
                            .size(61.dp)
                            .clip(CircleShape),
                        model = uiState.jobDetail.company.logo,
                        contentDescription = "Company Logo",
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            Text(
                text = uiState.jobDetail.position,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 6.dp),
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
            ) {
                Text(
                    text = uiState.jobDetail.company.name,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF1082DE),
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("company-details/${uiState.jobDetail.company.id}")
                    }
                )
                Image(
                    painter = painterResource(id = R.drawable.arrow_right_up),
                    contentDescription = "Arrow Right Up",
                    modifier = Modifier.size(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.job_icon),
                            contentDescription = "Job Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Full time",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.salary_range),
                            contentDescription = "Salary Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${uiState.jobDetail.miniSalary}-${uiState.jobDetail.maxiSalary}${uiState.jobDetail.currencyCode}",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.experience_icon),
                            contentDescription = "Experience Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "2+ years of experience",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.location_icon),
                            contentDescription = "Location Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${uiState.jobDetail.cityName}, ${uiState.jobDetail.stateName}",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.date_icon),
                            contentDescription = "Date Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Posted 14 days ago",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.application_icon),
                            contentDescription = "Applicants Icon",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${uiState.jobDetail.lastCVCount} Applicants",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 12.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Color(0xFFE4E7ED)),

                            )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Text(
                            text = "Job descriptions",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    ) {
                        val annotatedString =
                            remember(uiState.jobDetail.description) { htmlToAnnotatedString(uiState.jobDetail.description) }
                        Text(
                            text = annotatedString,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Color(0xFFE4E7ED)),

                            )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Text(
                            text = "Job requirements",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    ) {
                        val annotatedString =
                            remember(uiState.jobDetail.requirement) { htmlToAnnotatedString(uiState.jobDetail.requirement) }
                        Text(
                            text = annotatedString,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Color(0xFFE4E7ED)),

                            )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Text(
                            text = "Benefits & perks",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    ) {
                        val annotatedString = remember(uiState.jobDetail.benefitsAndPerks) {
                            htmlToAnnotatedString(uiState.jobDetail.benefitsAndPerks)
                        }
                        Text(
                            text = annotatedString,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 14.sp,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Color(0xFFE4E7ED)),

                            )
                    }

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gradient_line),
                            contentDescription = "Gradient Line",
                            modifier = Modifier
                                .size(4.dp, 18.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = "Similar Jobs",
                            modifier = Modifier.padding(start = 4.dp),
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp,
                        )
                    }
                }

                items(items.size) { index ->
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Transparent,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .height(80.dp)
                            .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.company_logo),
                                    contentDescription = "Company Logo",
                                    modifier = Modifier
                                        .size(48.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = "Designer",
                                    maxLines = 2,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 13.sp,
                                )

                                Text(
                                    modifier = Modifier.padding(top = 3.dp),
                                    text = "Yoma Bank",
                                    maxLines = 2,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF757575),
                                    fontSize = 12.sp,
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "MMK 400k-600k",
                                        maxLines = 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF757575),
                                        fontSize = 12.sp,
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Image(
                                        painter = painterResource(id = R.drawable.grey_line),
                                        contentDescription = "Grey Space Line",
                                        modifier = Modifier
                                            .size(1.dp, 8.dp),
                                        contentScale = ContentScale.Fit
                                    )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = "Yangon",
                                        maxLines = 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF757575),
                                        fontSize = 12.sp,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.wrapContentHeight()
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.save_unselected_icon),
                                    contentDescription = "Save Unselected Icon",
                                    modifier = Modifier
                                        .size(11.dp, 15.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Text(
                                    text = "",
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFF8CB2E),
                                    fontSize = 10.sp,
                                )

                                Text(
                                    text = "2 days left",
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFFF8CB2E),
                                    fontSize = 10.sp,
                                )
                            }
                        }
                    }
                }

            }
        }
    }

    Box {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = "Back Image",
            modifier = Modifier
                .padding(top = 50.dp, start = 16.dp)
                .size(24.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }

    AnimatedVisibility(
        visible = (isSaveItem),
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 70.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .border(1.dp, Color(0xFFE9FCF5), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFFE9FCF5), shape = MaterialTheme.shapes.medium),
            ) {

                Spacer(modifier = Modifier.width(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.save_job_icon),
                    contentDescription = "Save Job Image",
                    modifier = Modifier
                        .size(13.33.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Job is saved.",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        if (isJobApplied || uiState.appliedJobStatus == "applied") {
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .weight(2f)
                    .border(1.dp, Color(0xFF6A6A6A), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFF6A6A6A), shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Applied",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .weight(2f)
                    .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            if (viewModel.getBearerToken() != "") {
                                bottomBarVisibleAfterSignUp = true
                                //viewModel.getCandidateData()
                            } else {
                                bottomBarVisible = true
                            }
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Apply Job",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp,
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        if (!isSaveItem) {
            Log.d("state>>", "uiState.fetchSaveJobs.data!!.id null")
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(44.dp)
                    .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            if (viewModel.getToken() != "") {
                                viewModel.saveJob(jobId)
                            } else {
                                if (viewModel.getPageId() != "") {
                                    Toast
                                        .makeText(
                                            applicationContext,
                                            "Please LogIn/Register First!",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                    navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"profile"}")
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.save_unselected_icon),
                    contentDescription = "Save Unselected Image",
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        } else {
            Log.d("state>>else", uiState.fetchSaveJobs.data!!.id)
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(44.dp)
                    .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            viewModel.unSaveJob(uiState.fetchSaveJobs.data!!.id)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.save_selected_icon),
                    contentDescription = "Save Selected Image",
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }

        /*if(uiState.isSaveJobSuccess) {
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(44.dp)
                    .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                    .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium)
                    .clickable {
                        scope.launch {
                            viewModel.unSaveJob(uiState.fetchSaveJobs.id)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.save_selected_icon),
                    contentDescription = "Save Selected Image",
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }*/
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .systemBarsPadding()
        ) {

            if (bottomBarVisibleAfterSignUp) {
                ModalBottomSheetLayout(
                    sheetContent = {
                        when (step) {
                            "stepOne" -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp, 24.dp, 16.dp, 24.dp),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        androidx.compose.material.Text(
                                            text = "Application Process",
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
                                                    bottomBarVisibleAfterSignUp = false
                                                },
                                            alignment = Alignment.Center
                                        )
                                    }

                                    ApplyJobStateProgressBar(steps = stepOneAfterSignUp)

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        text = "Your Information",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W600,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 24.sp,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 32.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        item {
                                            Box(contentAlignment = Alignment.Center,
                                                modifier = Modifier.clickable {
                                                    launcher.launch("image/*")
                                                }) {
                                                if (selectedImageUri != null) {

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
                                                            .size(84.dp)
                                                            .clip(CircleShape)
                                                            .graphicsLayer {
                                                                shape = CircleShape
                                                            },
                                                        contentScale = ContentScale.Fit
                                                    )
                                                } else {
                                                    if (uiState.candidateData != null) {
                                                        if (uiState.candidateData.profile != null) {
                                                            Image(
                                                                painter = rememberGlidePainter(
                                                                    request = uiState.candidateData.profile.fullPath
                                                                ),
                                                                contentDescription = "Profile Icon",
                                                                modifier = Modifier
                                                                    .size(84.dp)
                                                                    .clip(CircleShape),
                                                                contentScale = ContentScale.Fit
                                                            )
                                                        } else {
                                                            Image(
                                                                painter = painterResource(id = R.drawable.camera),
                                                                contentDescription = "Profile Icon",
                                                                modifier = Modifier
                                                                    .size(84.dp)
                                                                    .clip(CircleShape),
                                                                contentScale = ContentScale.Fit
                                                            )
                                                        }
                                                    } else {
                                                        Image(
                                                            painter = painterResource(id = R.drawable.camera),
                                                            contentDescription = "Profile Icon",
                                                            modifier = Modifier
                                                                .size(84.dp)
                                                                .clip(CircleShape),
                                                            contentScale = ContentScale.Fit
                                                        )
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(16.dp))

                                            MultiStyleText(
                                                text1 = "Upload profile picture",
                                                color1 = Color(0xFF4A4A4A),
                                                text2 = "*",
                                                color2 = Color(0xFFffa558)
                                            )

                                            Spacer(modifier = Modifier.height(35.dp))

                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                MultiStyleText(
                                                    text1 = "Full name",
                                                    color1 = Color(0xFF4A4A4A),
                                                    text2 = "*",
                                                    color2 = Color(0xFFffa558)
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            OutlinedTextField(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(45.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFA7BAC5),
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    ),
                                                value = if (uiState.candidateData != null) uiState.candidateData.name else fullName,
                                                onValueChange = {
                                                    fullName = it
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter your name",
                                                        color = Color(0xFFAAAAAA)
                                                    )
                                                },
                                                textStyle = TextStyle(
                                                    fontWeight = FontWeight.W400,
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    color = Color(0xFFAAAAAA)
                                                ),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    textColor = Color(0xFFAAAAAA),
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
                                                        painter = painterResource(id = R.drawable.profile_name),
                                                        contentDescription = "Profile Icon",
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                    )
                                                },
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))

                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Email address",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF4A4A4A),
                                                fontSize = 14.sp
                                            )

                                            Spacer(modifier = Modifier.height(8.dp))

                                            OutlinedTextField(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(45.dp)
                                                    .border(
                                                        1.dp,
                                                        Color(0xFFA7BAC5),
                                                        RoundedCornerShape(4.dp)
                                                    )
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    ),
                                                value = if (uiState.candidateData != null) uiState.candidateData.email else email,
                                                onValueChange = {
                                                    email = it
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter email address",
                                                        color = Color(0xFFAAAAAA)
                                                    )
                                                },
                                                textStyle = TextStyle(
                                                    fontWeight = FontWeight.W400,
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    color = Color(0xFFAAAAAA)
                                                ),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    textColor = Color(0xFFAAAAAA),
                                                    backgroundColor = Color.Transparent,
                                                    cursorColor = Color(0xFF1ED292),
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent
                                                ),
                                                keyboardOptions = KeyboardOptions(
                                                    keyboardType = KeyboardType.Email,
                                                    imeAction = ImeAction.Done
                                                ),
                                                keyboardActions = KeyboardActions(
                                                    onDone = {
                                                        keyboardController?.hide()
                                                    }
                                                ),
                                                singleLine = true,
                                                trailingIcon = {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.email),
                                                        contentDescription = "Email Icon",
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                    )
                                                },
                                            )

                                            Spacer(modifier = Modifier.height(16.dp))

                                            Box(modifier = Modifier.fillMaxWidth()) {
                                                MultiStyleText(
                                                    text1 = "Resume or CV ",
                                                    color1 = Color(0xFF4A4A4A),
                                                    text2 = "*",
                                                    color2 = Color(0xFFffa558)
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))

                                            if (cvFile != null) {

                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(200.dp)
                                                        .background(
                                                            color = Color.Transparent,
                                                            shape = MaterialTheme.shapes.medium
                                                        )
                                                        .DashBorder(1.dp, Color(0xFF757575), 4.dp),
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
                                                                        cvFile = null
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
                                                                painter = painterResource(id = R.drawable.pdf_file_icon),
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

                                                }
                                            } else if (uiState.candidateData != null && cvFile == null && !isClearCV) {
                                                if (uiState.candidateData.cvFileName != "" && uiState.candidateData.cvFilePath != "") {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(200.dp)
                                                            .background(
                                                                color = Color.Transparent,
                                                                shape = MaterialTheme.shapes.medium
                                                            )
                                                            .DashBorder(
                                                                1.dp,
                                                                Color(0xFF757575),
                                                                4.dp
                                                            ),
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
                                                                            isClearCV = true
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
                                                                    painter = painterResource(id = R.drawable.pdf_file_icon),
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
                                                                    text = uiState.candidateData.cvFileName,
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
                                            } else {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(200.dp)
                                                        .background(
                                                            color = Color.Transparent,
                                                            shape = MaterialTheme.shapes.medium
                                                        )
                                                        .DashBorder(1.dp, Color(0xFF757575), 4.dp)
                                                        .clickable {
                                                            fileChooserLauncher.launch("application/pdf")
                                                        },
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

                                            Spacer(modifier = Modifier.height(16.dp))

                                            Text(
                                                modifier = Modifier.fillMaxWidth(),
                                                text = "Files Attachments",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF4A4A4A),
                                                fontSize = 14.sp
                                            )

                                            Spacer(modifier = Modifier.height(10.dp))

                                            AnimatedVisibility(
                                                visible = (uiState.candidateData != null && coverLetterFileList.isEmpty() && !isClearCoverLetter),
                                                enter = fadeIn(),
                                                exit = fadeOut()
                                            ) {
                                                AnimatedVisibility(
                                                    visible = (uiState.candidateData.coverFileName != "" && uiState.candidateData.coverFilePath != ""),
                                                    enter = fadeIn(),
                                                    exit = fadeOut()
                                                ) {
                                                    FlowRow(
                                                        maxItemsInEachRow = 1,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(bottom = 12.dp),
                                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        repeat(1) { fileInfo ->
                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .height(50.dp)
                                                                    .background(
                                                                        color = Color(0xFFF2F6FC),
                                                                        shape = MaterialTheme.shapes.medium
                                                                    )
                                                                    .DashBorder(
                                                                        1.dp,
                                                                        Color(0xFFA7BAC5),
                                                                        4.dp
                                                                    ),
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
                                                                        modifier = Modifier.weight(
                                                                            1f
                                                                        ),
                                                                        horizontalArrangement = Arrangement.Start,
                                                                        verticalAlignment = Alignment.CenterVertically
                                                                    ) {
                                                                        Image(
                                                                            painter = painterResource(
                                                                                id = R.drawable.pdf_file_logo
                                                                            ),
                                                                            contentDescription = "PDF Logo Icon",
                                                                            modifier = Modifier
                                                                                .size(24.dp)
                                                                        )

                                                                        Spacer(
                                                                            modifier = Modifier.width(
                                                                                16.dp
                                                                            )
                                                                        )

                                                                        Column(
                                                                            verticalArrangement = Arrangement.spacedBy(
                                                                                4.dp
                                                                            )
                                                                        ) {
                                                                            Text(
                                                                                text = uiState.candidateData.coverFileName,
                                                                                fontFamily = FontFamily(
                                                                                    Font(
                                                                                        R.font.poppins_regular
                                                                                    )
                                                                                ),
                                                                                fontWeight = FontWeight.W400,
                                                                                color = Color(
                                                                                    0xFF4A4A4A
                                                                                ),
                                                                                fontSize = 14.sp,
                                                                                maxLines = 1,
                                                                                softWrap = true,
                                                                                overflow = TextOverflow.Ellipsis
                                                                            )

                                                                            Text(
                                                                                text = "123 KB",
                                                                                fontFamily = FontFamily(
                                                                                    Font(
                                                                                        R.font.poppins_regular
                                                                                    )
                                                                                ),
                                                                                fontWeight = FontWeight.W400,
                                                                                color = Color(
                                                                                    0xFF757575
                                                                                ),
                                                                                fontSize = 8.sp
                                                                            )
                                                                        }
                                                                    }

                                                                    Image(
                                                                        painter = painterResource(id = R.drawable.x),
                                                                        contentDescription = "X Icon",
                                                                        modifier = Modifier
                                                                            .size(16.dp)
                                                                            .clickable {
                                                                                isClearCoverLetter =
                                                                                    true
                                                                            }
                                                                    )
                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            AnimatedVisibility(
                                                visible = coverLetterFileList.isNotEmpty(),
                                                enter = fadeIn(),
                                                exit = fadeOut()
                                            ) {
                                                FlowRow(
                                                    maxItemsInEachRow = 1,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 12.dp),
                                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                                ) {
                                                    repeat(coverLetterFileList.size) { fileInfo ->
                                                        Box(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(50.dp)
                                                                .background(
                                                                    color = Color(0xFFF2F6FC),
                                                                    shape = MaterialTheme.shapes.medium
                                                                )
                                                                .DashBorder(
                                                                    1.dp,
                                                                    Color(0xFFA7BAC5),
                                                                    4.dp
                                                                ),
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

                                                                    Spacer(
                                                                        modifier = Modifier.width(
                                                                            16.dp
                                                                        )
                                                                    )

                                                                    Column(
                                                                        verticalArrangement = Arrangement.spacedBy(
                                                                            4.dp
                                                                        )
                                                                    ) {
                                                                        Text(
                                                                            text = coverLetterFileList[fileInfo].fileName
                                                                                ?: "",
                                                                            fontFamily = FontFamily(
                                                                                Font(
                                                                                    R.font.poppins_regular
                                                                                )
                                                                            ),
                                                                            fontWeight = FontWeight.W400,
                                                                            color = Color(0xFF4A4A4A),
                                                                            fontSize = 14.sp,
                                                                            maxLines = 1,
                                                                            softWrap = true,
                                                                            overflow = TextOverflow.Ellipsis
                                                                        )

                                                                        Text(
                                                                            text = coverLetterFileList[fileInfo].fileSize
                                                                                ?: "",
                                                                            fontFamily = FontFamily(
                                                                                Font(
                                                                                    R.font.poppins_regular
                                                                                )
                                                                            ),
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
                                                                        .clickable {
                                                                            coverLetterFileList.remove(
                                                                                coverLetterFileList[fileInfo]
                                                                            )
                                                                        }
                                                                )
                                                            }

                                                        }
                                                    }
                                                }
                                            }

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
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .clickable {
                                                        fileListChooserLauncher.launch("*/*")
                                                    },
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

                                            Row(
                                                horizontalArrangement = Arrangement.Center,
                                                verticalAlignment = Alignment.Bottom,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(top = 24.dp),
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .clickable {
                                                            scope.launch {
                                                                if (uiState.candidateData != null) {
                                                                    if (uiState.candidateData.profile != null) {
                                                                        selectedImageUri?.let { uri ->
                                                                            val fileUri =
                                                                                AndroidFileUri(uri)
                                                                            viewModel.updateFile(
                                                                                fileUri,
                                                                                imageFileName,
                                                                                uiState.candidateData.profile.type,
                                                                                uiState.candidateData.profile.id,
                                                                            )
                                                                        }
                                                                    } else {
                                                                        selectedImageUri?.let { uri ->
                                                                            val fileUri =
                                                                                AndroidFileUri(uri)
                                                                            viewModel.uploadFile(
                                                                                fileUri,
                                                                                imageFileName,
                                                                                "profile"
                                                                            )
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            step = "stepTwo"
                                                        }
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
                                                        ),
                                                    contentAlignment = Alignment.Center,
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

                            "stepTwo" -> {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 24.dp,
                                            bottom = 24.dp
                                        )
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.Start
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        androidx.compose.material.Text(
                                            text = "Application Process",
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
                                                    bottomBarVisibleAfterSignUp = false
                                                },
                                            alignment = Alignment.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    ApplyJobStateProgressBar(steps = stepTwoAfterSignUp)

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        text = "Your Work Experience",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W600,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 24.sp,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Current Job Title",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF4A4A4A),
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFA7BAC5),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        value = jobTitle,
                                        onValueChange = {
                                            jobTitle = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter your current job title",
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            color = Color(0xFFAAAAAA)
                                        ),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = Color(0xFFAAAAAA),
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
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Current company name",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF4A4A4A),
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFA7BAC5),
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
                                                "Enter your current company name",
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            color = Color(0xFFAAAAAA)
                                        ),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = Color(0xFFAAAAAA),
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
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Working Since",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF4A4A4A),
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxWidth()
                                                .height(45.dp)
                                                .clickable {
                                                    monthDropDownExpanded = true
                                                }
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .background(
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Text(
                                                text = selectedItemMonth,
                                                modifier = Modifier.padding(start = 8.dp),
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFAAAAAA),
                                                fontSize = 14.sp,
                                            )
                                        }

                                        DropdownMenu(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp)
                                                .background(color = Color.Transparent)
                                                .fillMaxWidth()
                                                .border(
                                                    1.dp,
                                                    Color(0xFFE4E7ED),
                                                    RoundedCornerShape(8.dp)
                                                ),
                                            expanded = monthDropDownExpanded,
                                            onDismissRequest = { monthDropDownExpanded = false }
                                        ) {
                                            monthList.forEach { month ->
                                                DropdownMenuItem(
                                                    modifier = Modifier.fillMaxSize(),
                                                    onClick = {
                                                        selectedItemMonth = month
                                                        monthDropDownExpanded = false
                                                    }
                                                ) {
                                                    Text(
                                                        text = month,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFFAAAAAA),
                                                        fontSize = 14.sp,
                                                    )
                                                }
                                            }
                                        }
                                        /*OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFA7BAC5),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = startMonth,
                                    onValueChange = {
                                        startMonth = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Start month",
                                            color = Color(0xFFAAAAAA)
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFFAAAAAA)
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color(0xFFAAAAAA),
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
                                )*/

                                        OutlinedTextField(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxWidth()
                                                .height(45.dp)
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .background(
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            value = startYear,
                                            onValueChange = {
                                                startYear = it
                                            },
                                            placeholder = {
                                                Text(
                                                    "Start year",
                                                    fontWeight = FontWeight.W400,
                                                    fontSize = 14.sp,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    color = Color(0xFFAAAAAA)
                                                )
                                            },
                                            textStyle = TextStyle(
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color(0xFFAAAAAA),
                                                backgroundColor = Color.Transparent,
                                                cursorColor = Color(0xFF1ED292),
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    keyboardController?.hide()
                                                }
                                            ),
                                            singleLine = true,
                                        )
                                    }

                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.Bottom,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clickable {
                                                    var files: MutableList<FileUri?> = ArrayList()
                                                    var fileNames: MutableList<String?> =
                                                        ArrayList()
                                                    var fileIds: MutableList<String> =
                                                        ArrayList()
                                                    var fileTypes: MutableList<String> = ArrayList()
                                                    var existingFileIds: MutableList<String> =
                                                        ArrayList()

                                                    existingFileIds.clear()
                                                    fileNames.clear()
                                                    fileTypes.clear()
                                                    files.clear()

                                                    if (cvFile != null) {
                                                        val fileUri = AndroidFileUri(cvFile!!)
                                                        files.add(fileUri)
                                                        fileNames.add(cvFileName)
                                                        fileTypes.add("cv")
                                                    } else {
                                                        if (uiState.candidateData.cv != null) {
                                                            existingFileIds.add(uiState.candidateData.cv.id)
                                                            fileTypes.add("cv")
                                                        }
                                                    }

                                                    if (coverLetterFileList.isNotEmpty()) {
                                                        coverLetterFileList.let { file ->
                                                            file.map {
                                                                val fileUri = AndroidFileUri(it.uri)
                                                                files.add(fileUri)
                                                                fileNames.add(it.fileName)
                                                                fileTypes.add("cover_letter")
                                                            }
                                                        }
                                                    } else {
                                                        if (uiState.candidateData.coverLetter != null) {
                                                            existingFileIds.add(uiState.candidateData.coverLetter.id)
                                                            fileTypes.add("cover_letter")
                                                        }
                                                    }

                                                    Log.d("files>>>", files.toString())
                                                    Log.d("files>>>name", fileNames.toString())
                                                    Log.d("files>>>type", fileTypes.toString())
                                                    Log.d(
                                                        "files>>>existing",
                                                        existingFileIds.toString()
                                                    )

                                                    val validate =
                                                        (jobTitle.isNotBlank() && companyName.isNotBlank() && selectedItemMonth.isNotBlank() && startYear.isNotBlank())

                                                    if (!validate) {

                                                        if (jobTitle.isNullOrBlank()) {
                                                            Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Please Fill Job Title!",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }

                                                        if (companyName.isNullOrBlank()) {
                                                            Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Please Fill Company Name!",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }

                                                        if (selectedItemMonth.isNullOrBlank()) {
                                                            Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Please Choose Working Month!",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }

                                                        if (startYear.isNullOrBlank()) {
                                                            Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Please Choose Working Year!",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }
                                                    } else {
                                                        if (existingFileIds.isNotEmpty()) {
                                                            scope.launch {
                                                                viewModel.createApplication(
                                                                    uiState.jobDetail.id,
                                                                    uiState.jobDetail.company.subDomain,
                                                                    uiState.jobDetail.position,
                                                                    formattedDateTime,
                                                                    jobTitle,
                                                                    companyName,
                                                                    convertDate("$selectedItemMonth $startYear"),
//                                                                    fileNames,
//                                                                    files,
//                                                                    fileTypes,
                                                                    existingFileIds
                                                                )
                                                            }
                                                        } else {
                                                            fileIds.clear()

                                                            scope.launch {
                                                                val filesUri: MutableList<FileUri?> =
                                                                    ArrayList()
                                                                if (files.isNotEmpty()) {
                                                                    files.map {
                                                                        filesUri.add(it)
                                                                    }
                                                                }
                                                                viewModel.uploadMultipleFiles(
                                                                    filesUri, fileNames, fileTypes
                                                                )

                                                                if (uiState.isSuccessUploadMultipleFile) {
                                                                    Log.d(
                                                                        "toast>>",
                                                                        "isSuccessUploadMultipleFile"
                                                                    )
                                                                    if (uiState.multiFileList.isNotEmpty()) {
                                                                        uiState.multiFileList.map {
                                                                            fileIds.add(it.id)
                                                                        }
                                                                    }
                                                                }

                                                                if (fileIds.isNotEmpty()) {
                                                                    Log.d(
                                                                        "toast>>",
                                                                        "file ids not empty"
                                                                    )
                                                                    scope.launch {
                                                                        viewModel.createApplication(
                                                                            uiState.jobDetail.id,
                                                                            uiState.jobDetail.company.subDomain,
                                                                            uiState.jobDetail.position,
                                                                            formattedDateTime,
                                                                            jobTitle,
                                                                            companyName,
                                                                            convertDate("$selectedItemMonth $startYear"),
//                                                                        fileNames,
//                                                                        files,
//                                                                        fileTypes,
                                                                            fileIds
                                                                        )
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }
                                                }
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
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = "Submit",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W600,
                                                color = Color(0xFFFFFFFF),
                                                fontSize = 14.sp,
                                            )
                                        }
                                    }
                                }
                            }

                            "stepThree" -> {
                                Column(
                                    modifier = Modifier
                                        .padding(
                                            start = 16.dp,
                                            end = 16.dp,
                                            top = 24.dp,
                                            bottom = 24.dp
                                        )
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Top,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {

                                    Row(
                                        modifier = Modifier
                                            .padding(bottom = 8.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        androidx.compose.material.Text(
                                            text = "Application Process",
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
                                                    bottomBarVisibleAfterSignUp = false
                                                    step = "stepOne"
                                                },
                                            alignment = Alignment.Center
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))

                                    ApplyJobStateProgressBar(steps = stepThreeAfterSignUp)

                                    Spacer(modifier = Modifier.height(24.dp))

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

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            textAlign = TextAlign.Center,
                                            text = "Successfully applied the job!",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF757575),
                                            fontSize = 14.sp
                                        )
                                    }

                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.Bottom,
                                        modifier = Modifier
                                            .fillMaxSize(),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clickable {
                                                    bottomBarVisibleAfterSignUp = false
                                                    step = "stepOne"
                                                    if (viewModel.getPageId() != "") {
                                                        scope.launch {
                                                            navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"home"}")
                                                        }
                                                    }
                                                }
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
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = "Find Another Jobs",
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
                    when (step) {
                        "stepOne" -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp, 24.dp, 16.dp, 24.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Application Process",
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

                                Spacer(modifier = Modifier.height(16.dp))

                                ApplyJobStateProgressBar(steps = stepOne)

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Verify Phone Number",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    MultiStyleText(
                                        text1 = "Your phone number",
                                        color1 = Color(0xFF4A4A4A),
                                        text2 = "*",
                                        color2 = Color(0xFFffa558)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(2f)
                                            .height(50.dp)
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            )
                                    ) {
                                        OutlinedTextField(
                                            value = text,
                                            onValueChange = {
                                                text = it
                                            },
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp, 0.dp, 0.dp, 4.dp)
                                                ),
                                            placeholder = {
                                                Text(
                                                    "Enter your number",
                                                    color = Color(0xFFAAAAAA)
                                                )
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color(0xFFAAAAAA),
                                                backgroundColor = Color.Transparent,
                                                cursorColor = Color(0xFF1ED292),
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            textStyle = TextStyle(
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            ),
                                            //visualTransformation = PhoneNumberMask(),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Phone,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    keyboardController?.hide()
                                                    // Handle Done action if needed
                                                }
                                            ),
                                        )
                                    }

                                    if (isTimerRunning) {
                                        Button(
                                            onClick = {},
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Color(
                                                    0xFFA7BAC5
                                                )
                                            ),
                                            modifier = Modifier
                                                .weight(1f)
                                                .width(83.dp)
                                                .height(50.dp)
                                                .background(
                                                    color = Color(0xFFA7BAC5),
                                                    shape = MaterialTheme.shapes.medium
                                                )
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp)
                                                )
                                        )
                                        {
                                            Text(
                                                text = "Send OTP",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFFFFFFF),
                                                fontSize = 12.sp
                                            )
                                        }
                                    } else {
                                        Button(
                                            onClick = {
                                                if (text.isNotEmpty()) {
                                                    if (android.util.Patterns.PHONE.matcher(text)
                                                            .matches()
                                                    ) {
                                                        scope.launch {
                                                            isTimerRunning = true
                                                            viewModel.requestOTP(text)
                                                            viewModel.updatePhone(text)
                                                        }
                                                    } else {
                                                        Toast.makeText(
                                                            applicationContext,
                                                            "Phone Number is invalid..",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        applicationContext,
                                                        "Please enter phone number",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                backgroundColor = Color(
                                                    0xFF1ED292
                                                )
                                            ),
                                            modifier = Modifier
                                                .weight(1f)
                                                .width(83.dp)
                                                .height(50.dp)
                                                .background(
                                                    color = Color(0xFF1ED292),
                                                    shape = MaterialTheme.shapes.medium
                                                )
                                                .border(
                                                    1.dp,
                                                    Color(0xFF1ED292),
                                                    RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp)
                                                )
                                        )
                                        {
                                            Text(
                                                text = "Send OTP",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFFFFFFF),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "lost your phone number?",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF1082DE),
                                        fontSize = 12.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(20.dp))

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    MultiStyleText(
                                        text1 = "Enter OTP Code",
                                        color1 = Color(0xFF4A4A4A),
                                        text2 = "*",
                                        color2 = Color(0xFFffa558)
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    (0 until 6).forEach { index ->
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .focusRequester(focusRequesters[index])
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp)
                                                ),
                                            placeholder = {
                                                Text(
                                                    "—",
                                                    color = Color(0xFFAAAAAA),
                                                    style = TextStyle(textAlign = TextAlign.Center),
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            },
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color(0xFFAAAAAA),
                                                backgroundColor = Color.Transparent,
                                                cursorColor = Color(0xFFAAAAAA),
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            textStyle = androidx.compose.material.MaterialTheme.typography.body2.copy(
                                                textAlign = TextAlign.Center,
                                                color = Color(0xFFA7BAC5)
                                            ),
                                            singleLine = true,
                                            value = code.getOrNull(index = index)?.takeIf {
                                                it.isDigit()
                                            }?.toString() ?: "",
                                            onValueChange = { value: String ->
                                                if (focusRequesters[index].freeFocus()) {
                                                    val temp = code.toMutableList()
                                                    if (value == "") {
                                                        if (temp.size > index) {
                                                            temp.removeAt(index = index)
                                                            code = temp
                                                            focusRequesters.getOrNull(index)
                                                                ?.requestFocus()
                                                        }
                                                    } else {
                                                        if (code.size > index) {
                                                            temp[index] = value.getOrNull(0) ?: ' '
                                                        } else {
                                                            temp.add(value.getOrNull(0) ?: ' ')
                                                            code = temp
                                                            focusRequesters.getOrNull(index + 1)
                                                                ?.requestFocus()
                                                            //?: onFilled(code.joinToString(separator = "") )
                                                        }
                                                    }
                                                }
                                            },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                keyboardType = KeyboardType.Number,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    boxColor = Color(0xFF1ED292)
                                                    keyboardController?.hide()
                                                }
                                            ),
                                            //visualTransformation = PasswordVisualTransformation()
                                        )

                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.wrapContentSize(),
                                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.alert_circle_outline),
                                            contentDescription = "Alert Icon",
                                            modifier = Modifier.size(16.dp)
                                        )

                                        Text(
                                            text = "Get instead OTP code",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFFAAAAAA),
                                            fontSize = 12.sp,
                                            modifier = Modifier.wrapContentSize()
                                        )
                                    }

                                    if (isTimerRunning) {
                                        Row(
                                            modifier = Modifier.wrapContentSize(),
                                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "00:",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFA7BAC5),
                                                fontSize = 12.sp,
                                                modifier = Modifier.wrapContentSize()
                                            )

                                            Text(
                                                text = timerText,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFA7BAC5),
                                                fontSize = 12.sp,
                                                modifier = Modifier.wrapContentSize()
                                            )
                                        }
                                    }

                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                focusRequesters.forEach { it.freeFocus() }
                                                scope.launch {
                                                    viewModel.verifyOTP(code.joinToString(separator = ""))
                                                }
                                            }
                                            .height(50.dp)
                                            .fillMaxWidth()
                                            .background(
                                                color = boxColor,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Next",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFFFFFFFF),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }

                        "stepTwo" -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp, 24.dp, 16.dp, 24.dp),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Application Process",
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

                                ApplyJobStateProgressBar(steps = stepTwo)

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Your Information",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 32.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    item {
                                        Box(contentAlignment = Alignment.Center,
                                            modifier = Modifier.clickable {
                                                launcher.launch("image/*")
                                            }) {
                                            if (selectedImageUri != null) {

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
                                                        .size(84.dp)
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
                                                        .size(84.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Fit
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(16.dp))

                                        MultiStyleText(
                                            text1 = "Upload profile picture",
                                            color1 = Color(0xFF4A4A4A),
                                            text2 = "*",
                                            color2 = Color(0xFFffa558)
                                        )

                                        Spacer(modifier = Modifier.height(35.dp))

                                        Box(modifier = Modifier.fillMaxWidth()) {
                                            MultiStyleText(
                                                text1 = "Full name",
                                                color1 = Color(0xFF4A4A4A),
                                                text2 = "*",
                                                color2 = Color(0xFFffa558)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        OutlinedTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(45.dp)
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .background(
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            value = fullName,
                                            onValueChange = {
                                                fullName = it
                                            },
                                            placeholder = {
                                                Text(
                                                    "Enter your name",
                                                    color = Color(0xFFAAAAAA)
                                                )
                                            },
                                            textStyle = TextStyle(
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color(0xFFAAAAAA),
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
                                                    painter = painterResource(id = R.drawable.profile_name),
                                                    contentDescription = "Profile Icon",
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                )
                                            },
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Email address",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF4A4A4A),
                                            fontSize = 14.sp
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        OutlinedTextField(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(45.dp)
                                                .border(
                                                    1.dp,
                                                    Color(0xFFA7BAC5),
                                                    RoundedCornerShape(4.dp)
                                                )
                                                .background(
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            value = email,
                                            onValueChange = {
                                                email = it
                                            },
                                            placeholder = {
                                                Text(
                                                    "Enter your name",
                                                    color = Color(0xFFAAAAAA)
                                                )
                                            },
                                            textStyle = TextStyle(
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            ),
                                            colors = TextFieldDefaults.textFieldColors(
                                                textColor = Color(0xFFAAAAAA),
                                                backgroundColor = Color.Transparent,
                                                cursorColor = Color(0xFF1ED292),
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent
                                            ),
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Email,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = {
                                                    keyboardController?.hide()
                                                }
                                            ),
                                            singleLine = true,
                                            trailingIcon = {
                                                Image(
                                                    painter = painterResource(id = R.drawable.email),
                                                    contentDescription = "Email Icon",
                                                    modifier = Modifier
                                                        .size(16.dp)
                                                )
                                            },
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Box(modifier = Modifier.fillMaxWidth()) {
                                            MultiStyleText(
                                                text1 = "Resume or CV ",
                                                color1 = Color(0xFF4A4A4A),
                                                text2 = "*",
                                                color2 = Color(0xFFffa558)
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        if (cvFile != null) {

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .DashBorder(1.dp, Color(0xFF757575), 4.dp),
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
                                                                    cvFile = null
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
                                                            painter = painterResource(id = R.drawable.pdf_file_icon),
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

                                            }
                                        } else {

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(200.dp)
                                                    .background(
                                                        color = Color.Transparent,
                                                        shape = MaterialTheme.shapes.medium
                                                    )
                                                    .DashBorder(1.dp, Color(0xFF757575), 4.dp)
                                                    .clickable {
                                                        fileChooserLauncher.launch("application/pdf")
                                                    },
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

                                        Spacer(modifier = Modifier.height(16.dp))

                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Files Attachments",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF4A4A4A),
                                            fontSize = 14.sp
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))

                                        AnimatedVisibility(
                                            visible = coverLetterFileList.isNotEmpty(),
                                            enter = fadeIn(),
                                            exit = fadeOut()
                                        ) {
                                            FlowRow(
                                                maxItemsInEachRow = 1,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(bottom = 12.dp),
                                                verticalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                repeat(coverLetterFileList.size) { fileInfo ->
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(50.dp)
                                                            .background(
                                                                color = Color(0xFFF2F6FC),
                                                                shape = MaterialTheme.shapes.medium
                                                            )
                                                            .DashBorder(
                                                                1.dp,
                                                                Color(0xFFA7BAC5),
                                                                4.dp
                                                            ),
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
                                                                        text = coverLetterFileList[fileInfo].fileName
                                                                            ?: "",
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
                                                                        fontWeight = FontWeight.W400,
                                                                        color = Color(0xFF4A4A4A),
                                                                        fontSize = 14.sp,
                                                                        maxLines = 1,
                                                                        softWrap = true,
                                                                        overflow = TextOverflow.Ellipsis
                                                                    )

                                                                    Text(
                                                                        text = coverLetterFileList[fileInfo].fileSize
                                                                            ?: "",
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
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
                                                                    .clickable {
                                                                        coverLetterFileList.remove(
                                                                            coverLetterFileList[fileInfo]
                                                                        )
                                                                    }
                                                            )
                                                        }

                                                    }
                                                }
                                            }
                                        }

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
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                )
                                                .clickable {
                                                    fileListChooserLauncher.launch("*/*")
                                                },
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

                                        Row(
                                            horizontalArrangement = Arrangement.Center,
                                            verticalAlignment = Alignment.Bottom,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(top = 24.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clickable {

                                                        val validate =
                                                            (selectedImageUri != null && fullName.isNotBlank() && email.isNotBlank() && cvFile != null)
                                                        if (!validate) {
                                                            if (fullName == "") {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please fill name!",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            } else if (email == "") {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please fill email!",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            } else if (cvFile == null) {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please upload CV!",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            } else if (selectedImageUri == null) {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please upload Profile Picture!",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            }
                                                        } else {
                                                            scope.launch {
                                                                viewModel.createCandidate(
                                                                    fullName,
                                                                    email,
                                                                    uiState.jobDetail.position,
                                                                    "summary"
                                                                )
                                                            }
                                                        }
                                                    }
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
                                                    ),
                                                contentAlignment = Alignment.Center,
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

                        "stepThree" -> {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 24.dp,
                                        bottom = 24.dp
                                    )
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.Start
                            ) {

                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Application Process",
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

                                Spacer(modifier = Modifier.height(16.dp))

                                ApplyJobStateProgressBar(steps = stepThree)

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Your Work Experience",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Current Job Title",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFA7BAC5),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = jobTitle,
                                    onValueChange = {
                                        jobTitle = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter your current job title",
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            color = Color(0xFFAAAAAA)
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFFAAAAAA)
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color(0xFFAAAAAA),
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
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Current company name",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFA7BAC5),
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
                                            "Enter your current company name",
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            color = Color(0xFFAAAAAA)
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFFAAAAAA)
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color(0xFFAAAAAA),
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
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "Working Since",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .clickable {
                                                monthDropDownExpanded = true
                                            }
                                            .border(
                                                1.dp,
                                                Color(0xFFA7BAC5),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text(
                                            text = selectedItemMonth,
                                            modifier = Modifier.padding(start = 8.dp),
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFFAAAAAA),
                                            fontSize = 14.sp,
                                        )
                                    }

                                    DropdownMenu(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp)
                                            .background(color = Color.Transparent)
                                            .fillMaxWidth()
                                            .border(
                                                1.dp,
                                                Color(0xFFE4E7ED),
                                                RoundedCornerShape(8.dp)
                                            ),
                                        expanded = monthDropDownExpanded,
                                        onDismissRequest = { monthDropDownExpanded = false }
                                    ) {
                                        monthList.forEach { month ->
                                            DropdownMenuItem(
                                                modifier = Modifier.fillMaxSize(),
                                                onClick = {
                                                    selectedItemMonth = month
                                                    monthDropDownExpanded = false
                                                }
                                            ) {
                                                Text(
                                                    text = month,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W400,
                                                    color = Color(0xFFAAAAAA),
                                                    fontSize = 14.sp,
                                                )
                                            }
                                        }
                                    }

                                    /*OutlinedTextField(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .border(
                                            1.dp,
                                            Color(0xFFA7BAC5),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = startMonth,
                                    onValueChange = {
                                        startMonth = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Start month",
                                            color = Color(0xFFAAAAAA)
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFFAAAAAA)
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color(0xFFAAAAAA),
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
                                )*/

                                    OutlinedTextField(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .height(45.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFA7BAC5),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        value = startYear,
                                        onValueChange = {
                                            startYear = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Start year",
                                                fontWeight = FontWeight.W400,
                                                fontSize = 14.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                color = Color(0xFFAAAAAA)
                                            )
                                        },
                                        textStyle = TextStyle(
                                            fontWeight = FontWeight.W400,
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            color = Color(0xFFAAAAAA)
                                        ),
                                        colors = TextFieldDefaults.textFieldColors(
                                            textColor = Color(0xFFAAAAAA),
                                            backgroundColor = Color.Transparent,
                                            cursorColor = Color(0xFF1ED292),
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent
                                        ),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number,
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                keyboardController?.hide()
                                            }
                                        ),
                                        singleLine = true,
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                var files: MutableList<FileUri?> = ArrayList()
                                                var fileNames: MutableList<String?> =
                                                    ArrayList()
                                                var fileTypes: MutableList<String> = ArrayList()
                                                var fileIds: MutableList<String> =
                                                    ArrayList()

                                                files.clear()
                                                fileNames.clear()
                                                fileTypes.clear()
                                                fileIds.clear()

                                                if (cvFile != null) {
                                                    val fileUri = AndroidFileUri(cvFile!!)
                                                    files.add(fileUri)
                                                    fileNames.add(cvFileName)
                                                    fileTypes.add("cv")
                                                    if (coverLetterFileList.isNotEmpty()) {
                                                        coverLetterFileList.let { file ->
                                                            file.map {
                                                                val fileUri = AndroidFileUri(it.uri)
                                                                files.add(fileUri)
                                                                fileNames.add(it.fileName)
                                                                fileTypes.add("cover_letter")
                                                            }
                                                        }
                                                    }
                                                }

                                                Log.d("files>>>", files.toString())
                                                Log.d("files>>>name", fileNames.toString())
                                                Log.d("files>>>type", fileTypes.toString())

                                                val validate =
                                                    (files.isNotEmpty() && jobTitle.isNotBlank() && companyName.isNotBlank() && selectedItemMonth.isNotBlank() && startYear.isNotBlank())

                                                if (!validate) {
                                                    if (files.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please Upload CV!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }

                                                    if (jobTitle.isNullOrBlank()) {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please Fill Job Title!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }

                                                    if (companyName.isNullOrBlank()) {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please Fill Company Name!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }

                                                    if (selectedItemMonth.isNullOrBlank()) {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please Choose Working Month!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }

                                                    if (startYear.isNullOrBlank()) {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please Choose Working Year!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }
                                                } else {
                                                    scope.launch {
                                                        viewModel.uploadMultipleFiles(
                                                            files, fileNames, fileTypes
                                                        )

                                                        if (uiState.isSuccessUploadMultipleFile) {
                                                            Log.d(
                                                                "toast>>",
                                                                "isSuccessUploadMultipleFile"
                                                            )
                                                            if (uiState.multiFileList.isNotEmpty()) {
                                                                uiState.multiFileList.map {
                                                                    fileIds.add(it.id)
                                                                }
                                                            }
                                                        }

                                                        if (fileIds.isNotEmpty()) {
                                                            Log.d(
                                                                "toast>>",
                                                                "multi file list not null"
                                                            )
                                                            scope.launch {
                                                                viewModel.createApplication(
                                                                    uiState.jobDetail.id,
                                                                    uiState.jobDetail.company.subDomain,
                                                                    uiState.jobDetail.position,
                                                                    formattedDateTime,
                                                                    jobTitle,
                                                                    companyName,
                                                                    convertDate("$selectedItemMonth $startYear"),
//                                                            fileNames,
//                                                            files,
//                                                            fileTypes,
                                                                    fileIds
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
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
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Submit",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFFFFFFFF),
                                            fontSize = 14.sp,
                                        )
                                    }
                                }
                            }
                        }

                        "stepFour" -> {
                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 24.dp,
                                        bottom = 24.dp
                                    )
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Row(
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Application Process",
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
                                                step = "stepOne"
                                            },
                                        alignment = Alignment.Center
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                ApplyJobStateProgressBar(steps = stepFour)

                                Spacer(modifier = Modifier.height(24.dp))

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

                                Spacer(modifier = Modifier.height(32.dp))

                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "Successfully applied the job!",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF757575),
                                        fontSize = 14.sp
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clickable {
                                                bottomBarVisible = false
                                                step = "stepOne"
                                                if (viewModel.getPageId() != "") {
                                                    scope.launch {
                                                        navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"home"}")
                                                    }
                                                }
                                            }
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
                                            ),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Find Another Jobs",
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


}


@Composable
fun Boxes(
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
                x = (constraints.maxWidth - smallPlaceable.width) / 2,
                y = largePlaceable.height - smallPlaceable.height / 2
            )
        }
    }
}

data class FileInfo(
    val fileType: String,
    val uri: Uri,
    val fileName: String?,
    val fileSize: String?
)

@SuppressLint("Range")
fun getFileName(context: Context, uri: Uri): String {
    var fileName: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return fileName ?: ""
}

fun getFileSize(context: Context, uri: Uri): String? {
    var fileSize: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
        if (sizeIndex != -1) {
            val size = cursor.getLong(sizeIndex)
            fileSize = humanReadableByteCount(size, true)
        }
    }
    return fileSize
}

fun humanReadableByteCount(bytes: Long, si: Boolean): String {
    val unit = if (si) 1000 else 1024
    if (bytes < unit) return "$bytes B"
    val exp = (Math.log(bytes.toDouble()) / Math.log(unit.toDouble())).toInt()
    val pre = (if (si) "kMGTPE" else "KMGTPE")[exp - 1] + (if (si) "" else "i")
    return String.format("%.1f %sB", bytes / Math.pow(unit.toDouble(), exp.toDouble()), pre)
}

fun htmlToAnnotatedString(html: String): AnnotatedString {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    val result = buildAnnotatedString {
        val spans = spanned.getSpans(0, spanned.length, Any::class.java)
        var currentIndex = 0
        for (span in spans) {
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)
            append(spanned.subSequence(currentIndex, start).toString())
            withStyle(
                style = when (span) {
                    is android.text.style.StyleSpan -> SpanStyle(fontWeight = if (span.style == Typeface.BOLD) FontWeight.Bold else FontWeight.Normal)
                    else -> SpanStyle()
                }
            ) {
                append(spanned.subSequence(start, end).toString())
            }
            currentIndex = end
        }
        append(spanned.subSequence(currentIndex, spanned.length).toString())
    }
    return result
}
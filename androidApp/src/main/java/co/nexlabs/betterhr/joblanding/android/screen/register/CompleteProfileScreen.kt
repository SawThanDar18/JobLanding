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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
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
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.AndroidFileUri
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.data.ConvertYMDToMY
import co.nexlabs.betterhr.joblanding.android.screen.ErrorLayout
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CertificateUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.EducationUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.ExperienceUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.FilesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.LanguageUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.PositionUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.SkillUIModel
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import coil.compose.AsyncImage
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

data class AddMoreLanguageDataItem(
    var id: String,
    var language: String,
    var proficiencyLevels: String
)

data class AddMoreSkillDataItem(
    var id: String,
    var skill: String
)

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
    var updateSummary by remember { mutableStateOf("") }
    var companiesList: MutableList<CompaniesUIModel> = ArrayList()
    var experienceList: MutableList<ExperienceUIModel> = ArrayList()
    var educationList: MutableList<EducationUIModel> = ArrayList()
    var languageList: MutableList<LanguageUIModel> = ArrayList()
    var updateLanguageList: MutableList<LanguageUIModel> = ArrayList()
    var skillList: MutableList<SkillUIModel> = ArrayList()
    var updateSkillList: MutableList<SkillUIModel> = ArrayList()
    var certificateList: MutableList<CertificateUIModel> = ArrayList()
    var updateCertificateList: MutableList<CertificateUIModel> = ArrayList()
    val keyboardController = LocalSoftwareKeyboardController.current

    var startDateData by remember { mutableStateOf("") }
    var endDateData by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    var addSummaryVisible by remember { mutableStateOf(false) }
    var updateSummaryVisible by remember { mutableStateOf(false) }
    var addExperienceCompanyVisible by remember { mutableStateOf(false) }
    var addExperiencePositionVisible by remember { mutableStateOf(false) }
    var updateExperiencePositionVisible by remember { mutableStateOf(false) }
    var addEducationVisible by remember { mutableStateOf(false) }
    var updateEducationVisible by remember { mutableStateOf(false) }
    var addLanguageVisible by remember { mutableStateOf(false) }
    var updateLanguageVisible by remember { mutableStateOf(false) }
    var addSkillVisible by remember { mutableStateOf(false) }
    var updateSkillVisible by remember { mutableStateOf(false) }
    var addCertificateVisible by remember { mutableStateOf(false) }
    var updateCertificateVisible by remember { mutableStateOf(false) }

    var experienceIdForUpdateExperience by remember { mutableStateOf("") }
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

    var updatePosition by remember { mutableStateOf("") }
    var updateExperienceLevel by remember { mutableStateOf("") }
    var updateSelectedJobType by remember { mutableStateOf(jobTypes[0]) }

    var updateStartDate by remember { mutableStateOf("") }
    var updateEndDate by remember { mutableStateOf("") }
    var updateStartDateInYMD by remember { mutableStateOf("") }
    var updateEndDateInYMD by remember { mutableStateOf("") }
    var updateDescription by remember { mutableStateOf("") }

    var eduLevelList = listOf(
        "High School", "University Level", "Professional Certificate", "Diploma"
    )
    var eduLevelDropDownExpanded by remember { mutableStateOf(false) }

    var selectedCountry by remember { mutableStateOf("") }
    var university by remember { mutableStateOf("") }
    var selectedEducationLevel by remember { mutableStateOf("Choose Education Level") }
    var degree by remember { mutableStateOf("") }
    var fieldOfStudy by remember { mutableStateOf("") }
    var educationStartDate by remember { mutableStateOf("") }
    var educationEndDate by remember { mutableStateOf("") }
    var eduStartDateInYMD by remember { mutableStateOf("") }
    var eduEndDateInYMD by remember { mutableStateOf("") }
    var isCurrentStudying by remember { mutableStateOf(false) }
    var eduDescription by remember { mutableStateOf("") }

    var educationId by remember { mutableStateOf("") }
    var updateSelectedCountry by remember { mutableStateOf("") }
    var updateUniversity by remember { mutableStateOf("") }
    var updateSelectedEducationLevel by remember { mutableStateOf("") }
    var updateDegree by remember { mutableStateOf("") }
    var updateFieldOfStudy by remember { mutableStateOf("") }
    var updateEduStartDate by remember { mutableStateOf("") }
    var updateEduEndDate by remember { mutableStateOf("") }
    var updateEduStartDateInYMD by remember { mutableStateOf("") }
    var updateEduEndDateInYMD by remember { mutableStateOf("") }
    var updateIsCurrentStudying by remember { mutableStateOf(false) }
    var updateEduDescription by remember { mutableStateOf("") }

    var eduStartDateData by remember { mutableStateOf("") }
    var eduEndDateData by remember { mutableStateOf("") }

    var addMoreLanguageItem by remember {
        mutableStateOf(
            listOf(
                AddMoreLanguageDataItem(
                    "",
                    "",
                    ""
                )
            )
        )
    }
    var updateAddMoreLanguageItem by remember {
        mutableStateOf<List<AddMoreLanguageDataItem>>(
            emptyList()
        )
    }

    fun addMoreItem() {
        val newList = addMoreLanguageItem.toMutableList()
        newList.add(AddMoreLanguageDataItem("", "", ""))
        addMoreLanguageItem = newList
    }

    fun updateLanguageItem(index: Int, language: String, proficiencyLevels: String) {
        val updatedList = addMoreLanguageItem.toMutableList()
        updatedList[index] = AddMoreLanguageDataItem("", language, proficiencyLevels)
        addMoreLanguageItem = updatedList
    }

    fun updateLanguageItemWithId(index: Int, id: String, language: String, proficiencyLevels: String) {
        val updatedList = updateAddMoreLanguageItem.toMutableList()
        updatedList[index] = AddMoreLanguageDataItem(id, language, proficiencyLevels)
        updateAddMoreLanguageItem = updatedList
    }

    var addMoreSkillItem by remember {
        mutableStateOf(
            listOf(
                AddMoreSkillDataItem(
                    "",
                    "",
                )
            )
        )
    }
    var updateAddMoreSkillItem by remember {
        mutableStateOf<List<AddMoreSkillDataItem>>(
            emptyList()
        )
    }

    fun addMoreSkillItem() {
        val newList = addMoreSkillItem.toMutableList()
        newList.add(AddMoreSkillDataItem("", ""))
        addMoreSkillItem = newList
    }

    fun updateSkillItem(index: Int, skill: String) {
        val updatedList = addMoreSkillItem.toMutableList()
        updatedList[index] = AddMoreSkillDataItem("", skill)
        addMoreSkillItem = updatedList
    }

    fun updateSkillItemWithId(index: Int, id: String, skill: String) {
        val updatedList = updateAddMoreSkillItem.toMutableList()
        updatedList[index] = AddMoreSkillDataItem(id, skill)
        updateAddMoreSkillItem = updatedList
    }

    var certificateName by remember { mutableStateOf("") }
    var issuingOrganization by remember { mutableStateOf("") }
    var issueDate by remember { mutableStateOf("") }
    var expireDate by remember { mutableStateOf("") }
    var issueDateInYMD by remember { mutableStateOf("") }
    var expireDateInYMD by remember { mutableStateOf("") }
    var issueDateData by remember { mutableStateOf("") }
    var expireDateData by remember { mutableStateOf("") }
    var isCredentialExpire by remember { mutableStateOf(false) }
    var credentialUrl by remember { mutableStateOf("") }

    var certificateId by remember { mutableStateOf("") }
    var updateCertificateName by remember { mutableStateOf("") }
    var updateIssuingOrganization by remember { mutableStateOf("") }
    var updateIssueDate by remember { mutableStateOf("") }
    var updateExpireDate by remember { mutableStateOf("") }
    var updateIssueDateInYMD by remember { mutableStateOf("") }
    var updateExpireDateInYMD by remember { mutableStateOf("") }
    var updateIsCredentialExpire by remember { mutableStateOf(false) }
    var updateCredentialUrl by remember { mutableStateOf("") }

    var uriHandler = LocalUriHandler.current

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
                    viewModel.getCandidateData()
                }
                Log.d("state>>", "created")
            }
            Lifecycle.State.STARTED -> {
                scope.launch {
                    viewModel.getCandidateData()
                }
                Log.d("state>>", "started")
            }
            Lifecycle.State.RESUMED -> {
                scope.launch {
                    viewModel.getCandidateData()
                }
                Log.d("state>>", "resume")
            }
        }
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

        if (uiState.candidateData.education.isNotEmpty()) {
            uiState.candidateData.education.map {
                educationList.add(
                    EducationUIModel(
                        it.id,
                        it.countryName,
                        it.university,
                        it.educationLevel,
                        it.degree,
                        it.fieldOfStudy,
                        it.startDate,
                        it.endDate,
                        it.isCurrentStudying,
                        it.description
                    )
                )
            }
        }

        if (uiState.candidateData.language.isNotEmpty()) {
            uiState.candidateData.language.map {
                languageList.add(
                    LanguageUIModel(
                        it.id,
                        it.languageName,
                        it.proficiencyLevel
                    )
                )
            }
        }

        if (uiState.candidateData.skill.isNotEmpty()) {
            uiState.candidateData.skill.map {
                skillList.add(
                    SkillUIModel(
                        it.id,
                        it.skillName
                    )
                )
            }
        }

        if (uiState.candidateData.certificate.isNotEmpty()) {
            uiState.candidateData.certificate.map {
                certificateList.add(
                    CertificateUIModel(
                        it.id,
                        it.candidateId,
                        it.courseName,
                        it.issuingOrganization,
                        it.issueDate,
                        it.expireDate,
                        it.isExpire,
                        it.credentialUrl
                    )
                )
            }
        }

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

    LaunchedEffect(refreshing) {
        if (refreshing) {
            scope.launch {
                viewModel.getCandidateData()
            }
            refreshing = false
        }
    }

    LaunchedEffect(uiState.isSuccessUpdateSummary) {
        scope.launch {
            updateSummaryVisible = false
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

    LaunchedEffect(uiState.isSuccessUpdateCertificate) {
        scope.launch {
            updateCertificateVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessCreateCertificate) {
        scope.launch {
            addCertificateVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessUpdateSkill) {
        scope.launch {
            updateSkillVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessCreateSkill) {
        scope.launch {
            addSkillVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessUpdateLanguage) {
        scope.launch {
            updateLanguageVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessCreateLanguage) {
        scope.launch {
            addLanguageVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessUpdateEducation) {
        scope.launch {
            updateEducationVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessCreateEducation) {
        scope.launch {
            addEducationVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessCreateExperience) {
        scope.launch {
            addExperiencePositionVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.isSuccessUpdateExperience) {
        scope.launch {
            updateExperiencePositionVisible = false
            viewModel.getCandidateData()
        }
    }

    LaunchedEffect(uiState.getFileId) {
        if (bottomBarVisible) {
            scope.launch {
                bottomBarVisible = false
                viewModel.getCandidateData()
            }
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
        scope.launch {
            addExperienceCompanyVisible = false
            viewModel.getCandidateData()
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

                            if (summary == "") {
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
                                        painter = painterResource(id = R.drawable.plus),
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
                                                    updateSummaryVisible = true
                                                    updateSummary = summary
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

                            /*

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
                                        painter = painterResource(id = R.drawable.plus),
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
                                            painter = painterResource(id = R.drawable.plus),
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
                                        maxItemsInEachRow = 3,
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

                                                    AsyncImage(
                                                        model = company.file.fullPath,
                                                        contentDescription = "Profile Icon",
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                } else {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.add_company_logo),
                                                        contentDescription = "Profile Icon",
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .clip(CircleShape),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }

                                                Column(
                                                    verticalArrangement = Arrangement.spacedBy(4.dp)
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

                                                        if (index >= 0 && index < experienceList.size) {
                                                            var experience = experienceList[index]

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

                                                            if (experience.startDate != "0000:00:00 00:00:00") {
                                                                if (experience.endDate != "0000:00:00 00:00:00" && experience.endDate != "") {
                                                                    val sdf = SimpleDateFormat(
                                                                        "yyyy-MM-dd",
                                                                        Locale.US
                                                                    )
                                                                    val startDate =
                                                                        sdf.parse(experience.startDate)
                                                                    val endDate =
                                                                        sdf.parse(experience.endDate)

                                                                    calculateDateDifference(
                                                                        startDate,
                                                                        endDate
                                                                    ) { calculatedYears, calculatedMonths ->
                                                                        years = calculatedYears
                                                                        months = calculatedMonths
                                                                    }
                                                                }
                                                            }

                                                            if (experience.startDate != "0000:00:00 00:00:00") {
                                                                if (experience.endDate != "0000:00:00 00:00:00" && experience.endDate != "") {
                                                                    val sdf = SimpleDateFormat(
                                                                        "yyyy-MM-dd",
                                                                        Locale.US
                                                                    )
                                                                    val startDate =
                                                                        sdf.parse(experience.startDate)
                                                                    val endDate =
                                                                        sdf.parse(experience.endDate)

                                                                    calculateDateDifference(
                                                                        startDate,
                                                                        endDate
                                                                    ) { calculatedYears, calculatedMonths ->
                                                                        years = calculatedYears
                                                                        months = calculatedMonths
                                                                    }
                                                                }
                                                            } else {
                                                                years = 0
                                                                months = 0
                                                            }

                                                            if (experience.startDate != "0000:00:00 00:00:00") {
                                                                if (experience.endDate != "0000:00:00 00:00:00" && experience.endDate == "") {
                                                                    years = 1
                                                                    months = 1
                                                                }
                                                            } else {
                                                                years = 0
                                                                months = 0
                                                            }

                                                            var dateDiff =
                                                                if (years != 0 && months == 0) {
                                                                    "$years yr"
                                                                } else if (months != 0 && years == 0) {
                                                                    "$months mo"
                                                                } else if (years != 0 && months != 0) {
                                                                    "$years yr $months mo"
                                                                } else if (years == 1 && months == 1) {
                                                                    "Present"
                                                                } else {
                                                                    "--"
                                                                }

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
                                                        }
                                                    }
                                                }
                                            }

                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {

                                                if (experienceList.isNotEmpty()) {
                                                    FlowRow(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        maxItemsInEachRow = 1
                                                    ) {
                                                        repeat(experienceList.size) { index ->
                                                            var experience = experienceList[index]

                                                            Row(
                                                                modifier = Modifier.fillMaxWidth(),
                                                                horizontalArrangement = Arrangement.spacedBy(
                                                                    16.dp
                                                                )
                                                            ) {

                                                                Column {

                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            16.dp
                                                                        )
                                                                    )

                                                                    Box(
                                                                        modifier = Modifier
                                                                            .padding(start = 20.dp)
                                                                            .size(8.dp)
                                                                            .clip(CircleShape)
                                                                            .background(
                                                                                Color(
                                                                                    0xFF1ED292
                                                                                )
                                                                            )
                                                                    )

                                                                    Box(
                                                                        modifier = Modifier
                                                                            .padding(start = 23.dp)
                                                                            .width(1.dp)
                                                                            .height(100.dp)
                                                                            .background(
                                                                                Color(
                                                                                    0xFFE4E7ED
                                                                                )
                                                                            )
                                                                    )
                                                                }

                                                                Column {

                                                                    var date by remember {
                                                                        mutableStateOf(
                                                                            ""
                                                                        )
                                                                    }
                                                                    if (experience.startDate != "") {
                                                                        if (experience.startDate != "0000-00-00 00:00:00") {
                                                                            if (experience.endDate != "0000-00-00 00:00:00" && experience.endDate != "") {
                                                                                date = "${
                                                                                    ConvertYMDToMY(
                                                                                        experience.startDate
                                                                                    )
                                                                                } - ${
                                                                                    ConvertYMDToMY(
                                                                                        experience.endDate
                                                                                    )
                                                                                }"
                                                                            }
                                                                        } else {
                                                                            date = "--"
                                                                        }
                                                                    } else {
                                                                        date = "--"
                                                                    }

                                                                    if (experience.startDate != "") {
                                                                        if (experience.startDate != "0000-00-00 00:00:00") {
                                                                            if (experience.endDate != "0000-00-00 00:00:00" && experience.endDate == "") {
                                                                                date = "${
                                                                                    ConvertYMDToMY(
                                                                                        experience.startDate
                                                                                    )
                                                                                } - Current"
                                                                            }
                                                                        } else {
                                                                            date = "--"
                                                                        }
                                                                    } else {
                                                                        date = "--"
                                                                    }

                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            14.dp
                                                                        )
                                                                    )

                                                                    Row(
                                                                        horizontalArrangement = Arrangement.SpaceBetween,
                                                                        modifier = Modifier.fillMaxWidth()
                                                                    ) {
                                                                        Text(
                                                                            text = experience.title,
                                                                            fontFamily = FontFamily(
                                                                                Font(R.font.poppins_regular)
                                                                            ),
                                                                            fontWeight = FontWeight.W500,
                                                                            color = Color(0xFF4A4A4A),
                                                                            fontSize = 14.sp
                                                                        )
                                                                        Spacer(
                                                                            modifier = Modifier.width(
                                                                                8.dp
                                                                            )
                                                                        )
                                                                        Image(
                                                                            painter = painterResource(
                                                                                id = R.drawable.edit
                                                                            ),
                                                                            contentDescription = "Edit Icon",
                                                                            modifier = Modifier
                                                                                .size(24.dp)
                                                                                .clickable {
                                                                                    updateExperiencePositionVisible =
                                                                                        true

                                                                                    if (experience.startDate != "") {
                                                                                        if (experience.startDate != "0000-00-00 00:00:00" && (experience.endDate != "0000-00-00 00:00:00" && experience.endDate != "")) {
                                                                                            startDateData =
                                                                                                ConvertYMDToMY(
                                                                                                    experience.startDate
                                                                                                )
                                                                                            endDateData =
                                                                                                ConvertYMDToMY(
                                                                                                    experience.endDate
                                                                                                )
                                                                                        }
                                                                                    }

                                                                                    if (experience.startDate != "") {
                                                                                        if (experience.startDate != "0000-00-00 00:00:00" && experience.endDate == "") {
                                                                                            startDateData =
                                                                                                ConvertYMDToMY(
                                                                                                    experience.startDate
                                                                                                )
                                                                                            endDateData =
                                                                                                ""
                                                                                        }
                                                                                    }

                                                                                    companyIdForAddExperience =
                                                                                        company.id
                                                                                    experienceIdForUpdateExperience =
                                                                                        experience.id
                                                                                    updatePosition =
                                                                                        experience.title
                                                                                    updateExperienceLevel =
                                                                                        experience.experienceLevel
                                                                                    updateSelectedJobType =
                                                                                        experience.employmentType
                                                                                    updateStartDate =
                                                                                        startDateData
                                                                                    updateEndDate =
                                                                                        endDateData
                                                                                    updateDescription =
                                                                                        experience.description
                                                                                },
                                                                        )

                                                                    }

                                                                    Text(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        text = experience.employmentType,
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
                                                                        fontWeight = FontWeight.W500,
                                                                        color = Color(0xFF757575),
                                                                        fontSize = 12.sp
                                                                    )

                                                                    Text(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        text = "Yangon, Myanmar",
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
                                                                        fontWeight = FontWeight.W500,
                                                                        color = Color(0xFF757575),
                                                                        fontSize = 12.sp
                                                                    )

                                                                    Text(
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        text = date,
                                                                        fontFamily = FontFamily(
                                                                            Font(
                                                                                R.font.poppins_regular
                                                                            )
                                                                        ),
                                                                        fontWeight = FontWeight.W500,
                                                                        color = Color(0xFF757575),
                                                                        fontSize = 12.sp
                                                                    )

                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            4.dp
                                                                        )
                                                                    )

                                                                    Row {
                                                                        Text(
                                                                            text = "Description : ",
                                                                            fontFamily = FontFamily(
                                                                                Font(
                                                                                    R.font.poppins_regular
                                                                                )
                                                                            ),
                                                                            fontWeight = FontWeight.W500,
                                                                            color = Color(0xFF4A4A4A),
                                                                            fontSize = 12.sp
                                                                        )

                                                                        Text(
                                                                            text = experience.description,
                                                                            fontFamily = FontFamily(
                                                                                Font(
                                                                                    R.font.poppins_regular
                                                                                )
                                                                            ),
                                                                            fontWeight = FontWeight.W500,
                                                                            color = Color(0xFF4A4A4A),
                                                                            fontSize = 12.sp
                                                                        )
                                                                    }

                                                                    Spacer(
                                                                        modifier = Modifier.height(
                                                                            16.dp
                                                                        )
                                                                    )

                                                                }
                                                            }

                                                        }
                                                    }
                                                }
                                            }

                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                Text(
                                                    text = "+ Add Position",
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF42A5F5),
                                                    fontSize = 12.sp,
                                                    modifier = Modifier
                                                        .padding(
                                                            start = 48.dp,
                                                            top = 8.dp,
                                                            bottom = 16.dp
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

                                    Spacer(modifier = Modifier.height(8.dp))

                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                            if (educationList.isEmpty()) {
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
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Plus Icon",
                                        modifier = Modifier
                                            .size(24.dp),
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
                                            text = "Education",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.plus),
                                            contentDescription = "Plus Icon",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    addEducationVisible = true
                                                },
                                        )

                                    }

                                    FlowRow(
                                        maxItemsInEachRow = 1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                    ) {
                                        repeat(educationList.size) { index ->
                                            var education = educationList[index]

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = education.university,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 14.sp
                                                    )

                                                    Spacer(modifier = Modifier.width(8.dp))

                                                    Image(
                                                        painter = painterResource(id = R.drawable.edit),
                                                        contentDescription = "Edit Icon",
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .clickable {
                                                                updateEducationVisible = true

                                                                if (education.startDate != "0000-00-00 00:00:00" && (education.endDate != "0000-00-00 00:00:00" && education.endDate != "")) {
                                                                    eduStartDateData =
                                                                        ConvertYMDToMY(
                                                                            education.startDate
                                                                        )
                                                                    eduEndDateData =
                                                                        ConvertYMDToMY(
                                                                            education.endDate
                                                                        )
                                                                }

                                                                if (education.startDate != "0000-00-00 00:00:00" && education.endDate == "") {
                                                                    eduStartDateData =
                                                                        ConvertYMDToMY(
                                                                            education.startDate
                                                                        )
                                                                    eduEndDateData = ""
                                                                }

                                                                educationId = education.id
                                                                updateSelectedCountry =
                                                                    education.countryName
                                                                updateUniversity =
                                                                    education.university
                                                                updateSelectedEducationLevel =
                                                                    education.educationLevel
                                                                updateDegree = education.degree
                                                                updateFieldOfStudy =
                                                                    education.fieldOfStudy
                                                                updateEduStartDate =
                                                                    eduStartDateData
                                                                updateEduEndDate = eduEndDateData
                                                                updateIsCurrentStudying =
                                                                    education.isCurrentStudying
                                                                updateEduDescription =
                                                                    education.description
                                                            },
                                                        alignment = Alignment.Center
                                                    )
                                                }

                                                Text(
                                                    text = "${education.degree}, ${education.fieldOfStudy}",
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF757575),
                                                    fontSize = 12.sp
                                                )

                                                Text(
                                                    text = education.countryName,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF757575),
                                                    fontSize = 12.sp
                                                )

                                                var date by remember {
                                                    mutableStateOf(
                                                        ""
                                                    )
                                                }
                                                if (education.startDate != "0000-00-00 00:00:00") {
                                                    if (education.endDate != "0000-00-00 00:00:00" && education.endDate != "") {
                                                        date = "${
                                                            ConvertYMDToMY(
                                                                education.startDate
                                                            )
                                                        } - ${
                                                            ConvertYMDToMY(
                                                                education.endDate
                                                            )
                                                        }"
                                                    }
                                                } else {
                                                    date = "--"
                                                }

                                                if (education.startDate != "0000-00-00 00:00:00") {
                                                    if (education.endDate != "0000-00-00 00:00:00" && education.endDate == "") {
                                                        date = "${
                                                            ConvertYMDToMY(
                                                                education.startDate
                                                            )
                                                        } - Present"
                                                    }
                                                } else {
                                                    date = "--"
                                                }


                                                Text(
                                                    text = date,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF757575),
                                                    fontSize = 12.sp
                                                )

                                                Spacer(
                                                    modifier = Modifier.height(
                                                        4.dp
                                                    )
                                                )

                                                Row {
                                                    Text(
                                                        text = "Description : ",
                                                        fontFamily = FontFamily(
                                                            Font(
                                                                R.font.poppins_regular
                                                            )
                                                        ),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 12.sp
                                                    )

                                                    Text(
                                                        text = education.description,
                                                        fontFamily = FontFamily(
                                                            Font(
                                                                R.font.poppins_regular
                                                            )
                                                        ),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 12.sp
                                                    )
                                                }

                                                Spacer(
                                                    modifier = Modifier.height(
                                                        24.dp
                                                    )
                                                )

                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )*/

                            if (languageList.isEmpty()) {
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
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Plus Icon",
                                        modifier = Modifier
                                            .size(24.dp),
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
                                            text = "Language",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.edit),
                                                contentDescription = "Edit Icon",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        updateLanguageVisible = true

                                                        val updateList = updateAddMoreLanguageItem.toMutableList()
                                                        for (i in 0 until languageList.size) {
                                                            updateList.add(AddMoreLanguageDataItem(
                                                                languageList[i].id,
                                                                languageList[i].languageName,
                                                                languageList[i].proficiencyLevel
                                                            ))
                                                            updateAddMoreLanguageItem = updateList
                                                        }
                                                    },
                                            )

                                            Image(
                                                painter = painterResource(id = R.drawable.plus),
                                                contentDescription = "Plus Icon",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        addLanguageVisible = true
                                                    },
                                            )
                                        }

                                    }

                                    FlowRow(
                                        maxItemsInEachRow = 1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                    ) {
                                        repeat(languageList.size) { index ->
                                            var language = languageList[index]

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {

                                                if (index != 0) {

                                                    Spacer(modifier = Modifier.height(16.dp))

                                                    Box(
                                                        modifier = Modifier
                                                            .height(1.dp)
                                                            .fillMaxWidth()
                                                            .background(color = Color(0xFFE4E7ED))
                                                    )

                                                    Spacer(modifier = Modifier.height(16.dp))
                                                }

                                                Text(
                                                    text = language.languageName,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W500,
                                                    color = Color(0xFF4A4A4A),
                                                    fontSize = 14.sp
                                                )

                                                if (language.proficiencyLevel != "") {

                                                    Spacer(modifier = Modifier.width(8.dp))

                                                    Text(
                                                        text = language.proficiencyLevel,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W500,
                                                        color = Color(0xFF4A4A4A),
                                                        fontSize = 14.sp
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(2.dp)
                                    .background(color = Color(0xFFE4E7ED))
                            )

                            if (skillList.isEmpty()) {
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
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Plus Icon",
                                        modifier = Modifier
                                            .size(24.dp),
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
                                            text = "Other Skills",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.edit),
                                                contentDescription = "Edit Icon",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        updateSkillVisible = true

                                                        val updateList = updateAddMoreSkillItem.toMutableList()
                                                        for (i in 0 until skillList.size) {
                                                            updateList.add(AddMoreSkillDataItem(
                                                                skillList[i].id,
                                                                skillList[i].skillName
                                                            ))
                                                            updateAddMoreSkillItem = updateList
                                                        }
                                                    },
                                            )

                                            Image(
                                                painter = painterResource(id = R.drawable.plus),
                                                contentDescription = "Plus Icon",
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clickable {
                                                        addSkillVisible = true
                                                    },
                                            )
                                        }

                                    }

                                    FlowRow(
                                        maxItemsInEachRow = 1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                    ) {
                                        repeat(skillList.size) { index ->
                                            var skill = skillList[index]

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 16.dp),
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(8.dp)
                                                            .clip(CircleShape)
                                                            .background(
                                                                Color(
                                                                    0xFF6A6A6A
                                                                )
                                                            )
                                                    )

                                                    Text(
                                                        text = skill.skillName,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF6A6A6A),
                                                        fontSize = 14.sp
                                                    )
                                                }

                                                Spacer(
                                                    modifier = Modifier.height(
                                                        16.dp
                                                    )
                                                )
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

                            if (certificateList.isEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(72.dp)
                                        .padding(horizontal = 16.dp)
                                        .clickable {
                                            addCertificateVisible = true
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
                                        painter = painterResource(id = R.drawable.plus),
                                        contentDescription = "Plus Icon",
                                        modifier = Modifier
                                            .size(24.dp),
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
                                            text = "Certifications",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W600,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 16.sp
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.plus),
                                            contentDescription = "Plus Icon",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clickable {
                                                    addCertificateVisible = true
                                                },
                                        )

                                    }

                                    FlowRow(
                                        maxItemsInEachRow = 1,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                    ) {
                                        repeat(certificateList.size) { index ->
                                            var certificate = certificateList[index]

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                            ) {

                                                if (index != 0) {

                                                    Spacer(modifier = Modifier.height(16.dp))

                                                    Box(
                                                        modifier = Modifier
                                                            .height(1.dp)
                                                            .fillMaxWidth()
                                                            .background(color = Color(0xFFE4E7ED))
                                                    )

                                                    Spacer(modifier = Modifier.height(16.dp))
                                                }

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = certificate.courseName,
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF6A6A6A),
                                                        fontSize = 14.sp
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Image(
                                                        painter = painterResource(id = R.drawable.edit),
                                                        contentDescription = "Edit Icon",
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .clickable {
                                                                updateCertificateVisible = true

                                                                if (certificate.issueDate != "0000-00-00 00:00:00" && (certificate.expireDate != "0000-00-00 00:00:00" && certificate.expireDate != "")) {
                                                                    issueDateData =
                                                                        ConvertYMDToMY(
                                                                            certificate.issueDate
                                                                        )
                                                                    expireDateData =
                                                                        ConvertYMDToMY(
                                                                            certificate.expireDate
                                                                        )
                                                                }

                                                                if (certificate.issueDate != "0000-00-00 00:00:00" && certificate.expireDate == "") {
                                                                    issueDateData =
                                                                        ConvertYMDToMY(
                                                                            certificate.issueDate
                                                                        )
                                                                    expireDateData = ""
                                                                }

                                                                certificateId = certificate.id
                                                                updateCertificateName =
                                                                    certificate.courseName
                                                                updateIssuingOrganization =
                                                                    certificate.issuingOrganization
                                                                updateIssueDate = issueDateData
                                                                updateExpireDate = expireDateData
                                                                updateIsCredentialExpire =
                                                                    certificate.isExpire
                                                                updateCredentialUrl =
                                                                    certificate.credentialUrl
                                                            },
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(8.dp))

                                                Text(
                                                    text = certificate.issuingOrganization,
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W400,
                                                    color = Color(0xFF757575),
                                                    fontSize = 14.sp
                                                )

                                                var issueDateInMY by remember {
                                                    mutableStateOf(
                                                        ""
                                                    )
                                                }
                                                var expireDateInMY by remember {
                                                    mutableStateOf(
                                                        ""
                                                    )
                                                }

                                                if (certificate.issueDate != "0000-00-00 00:00:00") {
                                                    if (certificate.expireDate != "0000-00-00 00:00:00" && certificate.expireDate != "") {
                                                        issueDateInMY =
                                                            ConvertYMDToMY(certificate.issueDate)
                                                        expireDateInMY =
                                                            ConvertYMDToMY(certificate.expireDate)
                                                    }
                                                } else {
                                                    issueDateInMY = "--"
                                                    expireDateInMY = "--"
                                                }

                                                if (certificate.issueDate != "0000-00-00 00:00:00") {
                                                    if (certificate.expireDate != "0000-00-00 00:00:00" && certificate.expireDate == "") {
                                                        issueDateInMY =
                                                            ConvertYMDToMY(certificate.issueDate)
                                                        expireDateInMY = "No Expiration Date"
                                                    }
                                                } else {
                                                    issueDateInMY = "--"
                                                    expireDateInMY = "--"
                                                }

                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = "Issued $issueDateInMY",
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF757575),
                                                        fontSize = 14.sp
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .size(8.dp)
                                                            .clip(CircleShape)
                                                            .background(
                                                                Color(
                                                                    0xFF6A6A6A
                                                                )
                                                            )
                                                    )
                                                    Text(
                                                        text = "$expireDateInMY",
                                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                        fontWeight = FontWeight.W400,
                                                        color = Color(0xFF757575),
                                                        fontSize = 14.sp
                                                    )
                                                }

                                                if (certificate.credentialUrl != "") {

                                                    Spacer(
                                                        modifier = Modifier.height(
                                                            16.dp
                                                        )
                                                    )

                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                uriHandler.openUri(certificate.credentialUrl)
                                                            },
                                                        horizontalArrangement = Arrangement.spacedBy(
                                                            8.dp
                                                        ),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Text(
                                                            text = "Show credential",
                                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                            fontWeight = FontWeight.W400,
                                                            color = Color(0xFF1082DE),
                                                            fontSize = 14.sp
                                                        )

                                                        Image(
                                                            painter = painterResource(id = R.drawable.link_url),
                                                            contentDescription = "URL Link Icon",
                                                            modifier = Modifier
                                                                .size(16.dp),
                                                        )

                                                    }
                                                }
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))
                                }
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

    //update certificate
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (updateCertificateVisible) {

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
                                text = "Let’s add your certifications",
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
                                        updateCertificateVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about your certifications",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                Column {
                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Name",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = updateCertificateName,
                                        onValueChange = {
                                            updateCertificateName = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter certificate name",
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
                                        text1 = "Issuing organization",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = updateIssuingOrganization,
                                        onValueChange = {
                                            updateIssuingOrganization = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter issuing organization",
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
                                        text1 = "Issue date",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    DateButtonWithTrailingIcon(
                                        LocalContext.current,
                                        "Issue Date",
                                        updateIssueDate,
                                        onDateSelected = { date ->
                                            updateIssueDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateIssueDateInYMD = dateInYMD
                                        }, false
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Expiration date",
                                        color1 = if (isCredentialExpire) Color(0xFFD9D9D9) else Color(
                                            0xFF6A6A6A
                                        ),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    DateButtonWithTrailingIconForExpireDate(
                                        LocalContext.current,
                                        "Expiration Date",
                                        updateExpireDate,
                                        onDateSelected = { date ->
                                            updateExpireDate =
                                                if (updateIsCredentialExpire) "" else date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateExpireDateInYMD =
                                                if (updateIsCredentialExpire) "" else dateInYMD
                                        }, updateIsCredentialExpire
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 4.dp)
                                    ) {

                                        Checkbox(
                                            checked = updateIsCredentialExpire,
                                            onCheckedChange = { updateIsCredentialExpire = it },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color(0xFF1ED292),
                                                uncheckedColor = Color(0xFFE4E7ED),
                                                checkmarkColor = Color.White
                                            ),
                                            modifier = Modifier.padding(end = 4.dp)
                                        )

                                        Text(
                                            text = "This credential does not expire",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFFA7BAC5),
                                            fontSize = 12.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Credential URL",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = updateCredentialUrl,
                                        onValueChange = {
                                            updateCredentialUrl = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter credential URL",
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
                                        trailingIcon = {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_name),
                                                contentDescription = "URL Icon",
                                                modifier = Modifier
                                                    .size(16.dp)
                                            )
                                        },
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))
                                }

                                /*Text(
                                    text = "+ Add more certifications",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        addMoreSkillItem += 1
                                    }
                                )

                                Spacer(modifier = Modifier.height(24.dp))*/

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
                                                    (updateCertificateName != "" && updateIssuingOrganization != "" && updateIssueDate != "")

                                                if (validate) {
                                                    val outputFormat = SimpleDateFormat(
                                                        "yyyy-MM-dd",
                                                        Locale.getDefault()
                                                    )
                                                    val inputFormat = SimpleDateFormat(
                                                        "MMM yyyy",
                                                        Locale.getDefault()
                                                    )

                                                    if (updateIssueDate != "") {
                                                        if (updateIssueDateInYMD == "") {
                                                            val date: Date =
                                                                inputFormat.parse(
                                                                    updateIssueDate
                                                                )
                                                            updateIssueDateInYMD =
                                                                outputFormat.format(date)
                                                        }
                                                    }

                                                    if (updateExpireDate != "") {
                                                        if (updateExpireDateInYMD == "") {
                                                            val date: Date =
                                                                inputFormat.parse(
                                                                    updateExpireDate
                                                                )
                                                            updateExpireDateInYMD =
                                                                outputFormat.format(date)
                                                        }
                                                    }

                                                    scope.launch {
                                                        viewModel.updateCertificate(
                                                            certificateId,
                                                            updateCertificateName,
                                                            updateIssuingOrganization,
                                                            updateIssueDateInYMD,
                                                            if (updateIsCredentialExpire) "" else updateExpireDateInYMD,
                                                            updateIsCredentialExpire,
                                                            updateCredentialUrl
                                                        )
                                                    }
                                                } else {
                                                    if (updateCertificateName == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Certificate Name!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateIssuingOrganization == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Issuing organization!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateIssueDate == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please select issue date!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update certifications",
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

    //create certificate
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (addCertificateVisible) {

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
                                text = "Let’s add your certifications",
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
                                        addCertificateVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about your certifications",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                Column {
                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Name",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = certificateName,
                                        onValueChange = {
                                            certificateName = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter certificate name",
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
                                        text1 = "Issuing organization",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = issuingOrganization,
                                        onValueChange = {
                                            issuingOrganization = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter issuing organization",
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
                                        text1 = "Issue date",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    DateButtonWithTrailingIcon(
                                        LocalContext.current,
                                        "Issue Date",
                                        issueDate,
                                        onDateSelected = { date ->
                                            issueDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            issueDateInYMD = dateInYMD
                                        }, false
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Expiration date",
                                        color1 = if (isCredentialExpire) Color(0xFFD9D9D9) else Color(
                                            0xFF6A6A6A
                                        ),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    DateButtonWithTrailingIconForExpireDate(
                                        LocalContext.current,
                                        "Expiration Date",
                                        expireDate,
                                        onDateSelected = { date ->
                                            expireDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            expireDateInYMD = dateInYMD
                                        }, isCredentialExpire
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(start = 4.dp)
                                    ) {

                                        Checkbox(
                                            checked = isCredentialExpire,
                                            onCheckedChange = { isCredentialExpire = it },
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = Color(0xFF1ED292),
                                                uncheckedColor = Color(0xFFE4E7ED),
                                                checkmarkColor = Color.White
                                            ),
                                            modifier = Modifier.padding(end = 4.dp)
                                        )

                                        Text(
                                            text = "This credential does not expire",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFFA7BAC5),
                                            fontSize = 12.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(24.dp))

                                    MultiStyleTextForAddExperience(
                                        text1 = "Credential URL",
                                        color1 = Color(0xFF6A6A6A),
                                        text2 = "",
                                        color2 = Color(0xFF6A6A6A)
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
                                        value = credentialUrl,
                                        onValueChange = {
                                            credentialUrl = it
                                        },
                                        placeholder = {
                                            Text(
                                                "Enter credential URL",
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
                                        trailingIcon = {
                                            Image(
                                                painter = painterResource(id = R.drawable.profile_name),
                                                contentDescription = "URL Icon",
                                                modifier = Modifier
                                                    .size(16.dp)
                                            )
                                        },
                                    )

                                    Spacer(modifier = Modifier.height(24.dp))
                                }

                                /*Text(
                                    text = "+ Add more certifications",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        addMoreSkillItem += 1
                                    }
                                )

                                Spacer(modifier = Modifier.height(24.dp))*/

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
                                                    (certificateName != "" && issuingOrganization != "" && issueDateInYMD != "")

                                                if (validate) {
                                                    scope.launch {
                                                        viewModel.createCertificate(
                                                            certificateName,
                                                            issuingOrganization,
                                                            issueDateInYMD,
                                                            if (isCredentialExpire) "" else expireDateInYMD,
                                                            isCredentialExpire,
                                                            credentialUrl
                                                        )
                                                    }
                                                } else {
                                                    if (certificateName == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Certificate Name!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (issuingOrganization == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Issuing organization!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (issueDateInYMD == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please select issue date!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    }
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update certifications",
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

    //update skill
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (updateSkillVisible) {

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
                                text = "Let’s add your other skills",
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
                                        updateSkillVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about your other skills",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(updateAddMoreSkillItem.size) { index ->

                                        var id by remember { mutableStateOf(
                                            updateAddMoreSkillItem[index].id
                                        ) }

                                        var skill by remember { mutableStateOf(
                                            updateAddMoreSkillItem[index].skill
                                        ) }

                                        Column {
                                            Spacer(modifier = Modifier.height(24.dp))

                                            MultiStyleTextForAddExperience(
                                                text1 = "Skill ${index + 1}",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = skill,
                                                onValueChange = {
                                                    skill = it
                                                    updateSkillItemWithId(index, id, skill)
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter skill",
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
                                        }
                                    }
                                }

                                /*Text(
                                    text = "+ Add more skills",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            addMoreSkillItem()
                                        }
                                )

                                Spacer(modifier = Modifier.height(24.dp))*/

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

                                                if (updateAddMoreSkillItem.isNotEmpty()) {
                                                    for (item in updateAddMoreSkillItem) {
                                                        if (item.skill.isNotBlank()) {
                                                            viewModel.updateSkill(
                                                                item.id,
                                                                item.skill
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Please add Skill Name!",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update skills",
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

    //create skill
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (addSkillVisible) {

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
                                text = "Let’s add your other skills",
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
                                        addSkillVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about your other skills",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(addMoreSkillItem.size) { index ->

                                        var skill by remember { mutableStateOf(
                                            addMoreSkillItem[index].skill
                                        ) }

                                        Column {
                                            Spacer(modifier = Modifier.height(24.dp))

                                            MultiStyleTextForAddExperience(
                                                text1 = "Skill ${index + 1}",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = skill,
                                                onValueChange = {
                                                    skill = it
                                                    updateSkillItem(index, skill)
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter skill",
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
                                        }
                                    }
                                }

                                Text(
                                    text = "+ Add more skills",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            addMoreSkillItem()
                                        }
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

                                                if (addMoreSkillItem.isNotEmpty()) {
                                                    for (item in addMoreSkillItem) {
                                                        if (item.skill.isNotBlank()) {
                                                            viewModel.createSkill(
                                                                item.skill
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Please add Skill Name!",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update skills",
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

    //update language
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (updateLanguageVisible) {

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
                                text = "Let’s add your language skills",
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
                                        updateLanguageVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about language you speak",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(updateAddMoreLanguageItem.size) { index ->

                                        var id by remember {
                                            mutableStateOf(
                                                updateAddMoreLanguageItem[index].id
                                            )
                                        }
                                        var language by remember {
                                            mutableStateOf(
                                                updateAddMoreLanguageItem[index].language
                                            )
                                        }
                                        var proficiencyLevels by remember {
                                            mutableStateOf(
                                                updateAddMoreLanguageItem[index].proficiencyLevels
                                            )
                                        }


                                        Column {
                                            Spacer(modifier = Modifier.height(24.dp))

                                            MultiStyleTextForAddExperience(
                                                text1 = "Language",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = language,
                                                onValueChange = {
                                                    language = it
                                                    updateLanguageItemWithId(
                                                        index,
                                                        id,
                                                        language,
                                                        proficiencyLevels
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter language",
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
                                                text1 = "Proficiency levels",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = proficiencyLevels,
                                                onValueChange = {
                                                    proficiencyLevels = it
                                                    updateLanguageItemWithId(
                                                        index,
                                                        id,
                                                        language,
                                                        proficiencyLevels
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter proficiency levels",
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
                                        }
                                    }
                                }

                                /*Text(
                                    text = "+ Add more language",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            addMoreItem()
                                        }
                                )

                                Spacer(modifier = Modifier.height(24.dp))*/

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

                                                if (updateAddMoreLanguageItem.isNotEmpty()) {
                                                    scope.launch {
                                                        for (item in updateAddMoreLanguageItem) {
                                                            if (item.id.isNotBlank() && (item.language.isNotBlank() || item.proficiencyLevels.isNotBlank())) {
                                                                viewModel.updateLanguage(
                                                                    item.id,
                                                                    item.language,
                                                                    item.proficiencyLevels
                                                                )
                                                            } else {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please add Language Name",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Please add Language Name!",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update language skills",
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

    //create language
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (addLanguageVisible) {

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
                                text = "Let’s add your language skills",
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
                                        addLanguageVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Indicated required",
                                    color1 = Color(0xFF757575),
                                    text2 = "*",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Tell employer about language you speak",
                                    color = Color(0xFF6A6A6A),
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                )

                                FlowRow(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    repeat(addMoreLanguageItem.size) { index ->

                                        var language by remember {
                                            mutableStateOf(
                                                addMoreLanguageItem[index].language
                                            )
                                        }
                                        var proficiencyLevels by remember {
                                            mutableStateOf(
                                                addMoreLanguageItem[index].proficiencyLevels
                                            )
                                        }


                                        Column {
                                            Spacer(modifier = Modifier.height(24.dp))

                                            MultiStyleTextForAddExperience(
                                                text1 = "Language",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = language,
                                                onValueChange = {
                                                    language = it
                                                    updateLanguageItem(
                                                        index,
                                                        language,
                                                        proficiencyLevels
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter language",
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
                                                text1 = "Proficiency levels",
                                                color1 = Color(0xFF757575),
                                                text2 = "",
                                                color2 = Color(0xFF757575)
                                            )

                                            Spacer(modifier = Modifier.height(4.dp))

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
                                                value = proficiencyLevels,
                                                onValueChange = {
                                                    proficiencyLevels = it
                                                    updateLanguageItem(
                                                        index,
                                                        language,
                                                        proficiencyLevels
                                                    )
                                                },
                                                placeholder = {
                                                    Text(
                                                        "Enter proficiency levels",
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
                                        }
                                    }
                                }

                                Text(
                                    text = "+ Add more language",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF1ED292),
                                    fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            addMoreItem()
                                        }
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

                                                if (addMoreLanguageItem.isNotEmpty()) {
                                                    scope.launch {
                                                        for (item in addMoreLanguageItem) {
                                                            if (item.language.isNotBlank() || item.proficiencyLevels.isNotBlank()) {
                                                                viewModel.createLanguage(
                                                                    item.language,
                                                                    item.proficiencyLevels
                                                                )
                                                            } else {
                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Please add Language Name!",
                                                                        Toast.LENGTH_LONG
                                                                    )
                                                                    .show()
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Please add Language!",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                            },
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(
                                            text = "Update language skills",
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

    //update education
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {
        var searchText = rememberSaveable { mutableStateOf("") }
        val countries = remember { mutableStateOf(getCountries()) }
        var filteredCountries: MutableList<String> = ArrayList()

        if (updateEducationVisible) {

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
                                text = "Let’s add your education",
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
                                        updateEducationVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        var showCountry by remember { mutableStateOf(false) }
                        val resultList: MutableList<String> = ArrayList()
                        for (country in countries.value) {
                            if (country.lowercase(Locale.getDefault())
                                    .contains(searchText.value.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(country)
                            }
                        }
                        filteredCountries.clear()
                        filteredCountries = resultList

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Country",
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
                                    value = updateSelectedCountry,
                                    onValueChange = {
                                        updateSelectedCountry = it
                                        showCountry = true
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter country",
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

                                if (filteredCountries.isNotEmpty() && showCountry) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                            .height(194.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFE4E7ED),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                    ) {
                                        FlowRow(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 8.dp, end = 8.dp),
                                            maxItemsInEachRow = 1
                                        ) {
                                            repeat(filteredCountries.size) { index ->
                                                var item = filteredCountries[index]
                                                Column(
                                                    modifier = Modifier.clickable {
                                                        selectedCountry = item
                                                        searchText.value = item
                                                        showCountry = false
                                                    }
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(top = 6.dp),
                                                        text = item
                                                    )
                                                    //Divider(modifier = Modifier.padding(top = 6.dp))
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "School/ University",
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
                                    value = updateUniversity,
                                    onValueChange = {
                                        updateUniversity = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter School/ University",
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
                                    text1 = "Education Level",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .clickable {
                                            eduLevelDropDownExpanded = true
                                        }
                                        .border(
                                            1.dp,
                                            Color(0xFFE4E7ED),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = updateSelectedEducationLevel,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFF4A4A4A),
                                        modifier = Modifier.padding(horizontal = 16.dp)
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
                                            RoundedCornerShape(4.dp)
                                        ),
                                    expanded = eduLevelDropDownExpanded,
                                    onDismissRequest = { eduLevelDropDownExpanded = false }
                                ) {
                                    eduLevelList.forEach { eduLevel ->
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxSize(),
                                            onClick = {
                                                updateSelectedEducationLevel = eduLevel
                                                eduLevelDropDownExpanded = false
                                            }
                                        ) {
                                            Text(
                                                text = eduLevel,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF4A4A4A),
                                                fontSize = 14.sp,
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Degree",
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
                                    value = updateDegree,
                                    onValueChange = {
                                        updateDegree = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Type Degree",
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
                                    text1 = "Field of Study",
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
                                    value = updateFieldOfStudy,
                                    onValueChange = {
                                        updateFieldOfStudy = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Type Field Name",
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
                                        updateEduStartDate,
                                        onDateSelected = { date ->
                                            updateEduStartDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateEduStartDateInYMD = dateInYMD
                                        }, isCurrentStudying
                                    )

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

                                    DateButtonForEducationEndDate(
                                        LocalContext.current,
                                        "End Date",
                                        updateEduEndDate,
                                        onDateSelected = { date ->
                                            updateEduEndDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateEduEndDateInYMD = dateInYMD
                                        }, isCurrentStudying
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 4.dp)
                                ) {
                                    Checkbox(
                                        checked = updateIsCurrentStudying,
                                        onCheckedChange = {
                                            updateIsCurrentStudying = it
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFF1690F3),
                                            uncheckedColor = Color(0xFFE4E7ED),
                                            checkmarkColor = Color.White
                                        ),
                                        modifier = Modifier.padding(end = 4.dp)
                                    )

                                    Text(
                                        text = "Currently Studying",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W500,
                                        color = Color(0xFF1690F3),
                                        fontSize = 12.sp
                                    )
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
                                    value = updateEduDescription,
                                    onValueChange = {
                                        updateEduDescription = it
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
                                                    (updateSelectedCountry != "" && updateUniversity != "" && updateSelectedEducationLevel != "" && updateDegree != "" && updateFieldOfStudy != "" && updateEduStartDate != "")
                                                if (validate) {
                                                    scope.launch {
                                                        val outputFormat = SimpleDateFormat(
                                                            "yyyy-MM-dd",
                                                            Locale.getDefault()
                                                        )
                                                        val inputFormat = SimpleDateFormat(
                                                            "MMM yyyy",
                                                            Locale.getDefault()
                                                        )

                                                        if (updateEduStartDate != "") {
                                                            if (updateEduStartDateInYMD == "") {
                                                                val date: Date =
                                                                    inputFormat.parse(
                                                                        updateEduStartDate
                                                                    )
                                                                updateEduStartDateInYMD =
                                                                    outputFormat.format(date)
                                                            }
                                                        }

                                                        if (updateEduEndDate != "") {
                                                            if (updateEduEndDateInYMD == "") {
                                                                val date: Date =
                                                                    inputFormat.parse(
                                                                        updateEduEndDate
                                                                    )
                                                                updateEduEndDateInYMD =
                                                                    outputFormat.format(date)
                                                            }
                                                        }

                                                        viewModel.updateEducation(
                                                            educationId,
                                                            updateSelectedCountry,
                                                            updateUniversity,
                                                            updateSelectedEducationLevel,
                                                            updateDegree,
                                                            updateFieldOfStudy,
                                                            updateEduStartDateInYMD,
                                                            if (updateIsCurrentStudying) "" else updateEduEndDateInYMD,
                                                            updateIsCurrentStudying,
                                                            updateEduDescription
                                                        )
                                                    }
                                                } else {
                                                    if (updateSelectedCountry == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please select Country!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateUniversity == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add School/ University!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateSelectedEducationLevel == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Education Level!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateDegree == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Degree!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateFieldOfStudy == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Field of Study!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateEduStartDate == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Start Date!",
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

    //create education
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {
        var searchText = rememberSaveable { mutableStateOf("") }
        val countries = remember { mutableStateOf(getCountries()) }
        var filteredCountries: MutableList<String> = ArrayList()

        if (addEducationVisible) {

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
                                text = "Let’s add your education",
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
                                        addEducationVisible = false
                                    },
                                alignment = Alignment.Center
                            )
                        }

                        /*filteredCountries = if (searchText.value.isEmpty()) {
                                        countries.value
                                    } else {*/
                        var showCountry by remember { mutableStateOf(false) }
                        val resultList: MutableList<String> = ArrayList()
                        for (country in countries.value) {
                            if (country.lowercase(Locale.getDefault())
                                    .contains(searchText.value.lowercase(Locale.getDefault()))
                            ) {
                                resultList.add(country)
                            }
                        }
                        filteredCountries.clear()
                        filteredCountries = resultList
                        //}

                        LazyColumn {
                            item {
                                Spacer(modifier = Modifier.height(32.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Country",
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
                                    value = searchText.value,
                                    onValueChange = {
                                        searchText.value = it
                                        showCountry = true
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter country",
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

                                if (filteredCountries.isNotEmpty() && showCountry) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp)
                                            .height(194.dp)
                                            .border(
                                                1.dp,
                                                Color(0xFFE4E7ED),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .background(
                                                color = Color.Transparent,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                    ) {
                                        FlowRow(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(start = 8.dp, end = 8.dp),
                                            maxItemsInEachRow = 1
                                        ) {
                                            repeat(filteredCountries.size) { index ->
                                                var item = filteredCountries[index]
                                                Column(
                                                    modifier = Modifier.clickable {
                                                        selectedCountry = item
                                                        searchText.value = item
                                                        showCountry = false
                                                    }
                                                ) {
                                                    Text(
                                                        modifier = Modifier.padding(top = 6.dp),
                                                        text = item
                                                    )
                                                    //Divider(modifier = Modifier.padding(top = 6.dp))
                                                }
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "School/ University",
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
                                    value = university,
                                    onValueChange = {
                                        university = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Enter School/ University",
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
                                    text1 = "Education Level",
                                    color1 = Color(0xFF757575),
                                    text2 = "",
                                    color2 = Color(0xFF757575)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .height(45.dp)
                                        .clickable {
                                            eduLevelDropDownExpanded = true
                                        }
                                        .border(
                                            1.dp,
                                            Color(0xFFE4E7ED),
                                            RoundedCornerShape(4.dp)
                                        )
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        text = selectedEducationLevel,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        color = Color(0xFF4A4A4A),
                                        modifier = Modifier.padding(horizontal = 16.dp)
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
                                            RoundedCornerShape(4.dp)
                                        ),
                                    expanded = eduLevelDropDownExpanded,
                                    onDismissRequest = { eduLevelDropDownExpanded = false }
                                ) {
                                    eduLevelList.forEach { eduLevel ->
                                        DropdownMenuItem(
                                            modifier = Modifier.fillMaxSize(),
                                            onClick = {
                                                selectedEducationLevel = eduLevel
                                                eduLevelDropDownExpanded = false
                                            }
                                        ) {
                                            Text(
                                                text = eduLevel,
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFF4A4A4A),
                                                fontSize = 14.sp,
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                MultiStyleTextForAddExperience(
                                    text1 = "Degree",
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
                                    value = degree,
                                    onValueChange = {
                                        degree = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Type Degree",
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
                                    text1 = "Field of Study",
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
                                    value = fieldOfStudy,
                                    onValueChange = {
                                        fieldOfStudy = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Type Field Name",
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
                                        educationStartDate,
                                        onDateSelected = { date ->
                                            educationStartDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            eduStartDateInYMD = dateInYMD
                                        }, isCurrentStudying
                                    )

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

                                    DateButtonForEducationEndDate(
                                        LocalContext.current,
                                        "End Date",
                                        educationEndDate,
                                        onDateSelected = { date ->
                                            educationEndDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            eduEndDateInYMD = dateInYMD
                                        }, isCurrentStudying
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 4.dp)
                                ) {

                                    Checkbox(
                                        checked = isCurrentStudying,
                                        onCheckedChange = { isCurrentStudying = it },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = Color(0xFF1690F3),
                                            uncheckedColor = Color(0xFFE4E7ED),
                                            checkmarkColor = Color.White
                                        ),
                                        modifier = Modifier.padding(end = 4.dp)
                                    )

                                    Text(
                                        text = "Currently Studying",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W500,
                                        color = Color(0xFF1690F3),
                                        fontSize = 12.sp
                                    )
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
                                    value = eduDescription,
                                    onValueChange = {
                                        eduDescription = it
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
                                                    (selectedCountry != "" && university != "" && selectedEducationLevel != "" && degree != "" && fieldOfStudy != "" && educationStartDate != "")
                                                if (validate) {
                                                    scope.launch {
                                                        viewModel.createEducation(
                                                            selectedCountry,
                                                            university,
                                                            selectedEducationLevel,
                                                            degree,
                                                            fieldOfStudy,
                                                            eduStartDateInYMD,
                                                            if (isCurrentStudying) "" else eduEndDateInYMD,
                                                            isCurrentStudying,
                                                            eduDescription
                                                        )
                                                    }
                                                } else {
                                                    if (selectedCountry == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please select Country!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (university == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add School/ University!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (selectedEducationLevel == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Education Level!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (degree == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Degree!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (fieldOfStudy == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Field of Study!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (educationStartDate == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Start Date!",
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

        if (updateExperiencePositionVisible) {

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
                                        updateExperiencePositionVisible = false
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
                                    value = updatePosition,
                                    onValueChange = {
                                        updatePosition = it
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
                                    value = updateExperienceLevel,
                                    onValueChange = {
                                        updateExperienceLevel = it
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
                                            onClick = { updateSelectedJobType = jobType },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                backgroundColor = Color.Transparent,
                                                contentColor = if (updateSelectedJobType == jobType) Color(
                                                    0xFF1ED292
                                                ) else Color(0xFFE1E1E1)
                                            ),
                                            border = if (updateSelectedJobType == jobType) {
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
                                                color = if (updateSelectedJobType == jobType) Color(
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
                                        updateStartDate,
                                        onDateSelected = { date ->
                                            updateStartDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateStartDateInYMD = dateInYMD
                                        }, false
                                    )

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
                                        updateEndDate,
                                        onDateSelected = { date ->
                                            updateEndDate = date
                                        },
                                        onDateSelectedInYMD = { dateInYMD ->
                                            updateEndDateInYMD = dateInYMD
                                        }, false
                                    )
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
                                    value = updateDescription,
                                    onValueChange = {
                                        updateDescription = it
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

                                                val outputFormat = SimpleDateFormat(
                                                    "yyyy-MM-dd",
                                                    Locale.getDefault()
                                                )
                                                val inputFormat = SimpleDateFormat(
                                                    "MMM yyyy",
                                                    Locale.getDefault()
                                                )

                                                if (updateStartDate != "") {
                                                    if (updateStartDateInYMD == "") {
                                                        val date: Date =
                                                            inputFormat.parse(updateStartDate)
                                                        updateStartDateInYMD =
                                                            outputFormat.format(date)
                                                    }
                                                }

                                                if (updateEndDate != "") {
                                                    if (updateEndDateInYMD == "") {
                                                        val date: Date =
                                                            inputFormat.parse(updateEndDate)
                                                        updateEndDateInYMD =
                                                            outputFormat.format(date)
                                                    }
                                                }

                                                var validate =
                                                    (updatePosition != "" && updateExperienceLevel != "" && updateSelectedJobType != "" && updateStartDateInYMD != "")
                                                if (validate) {
                                                    scope.launch {
                                                        viewModel.updateExperience(
                                                            experienceIdForUpdateExperience,
                                                            companyIdForAddExperience,
                                                            updatePosition,
                                                            "",
                                                            updateExperienceLevel,
                                                            updateSelectedJobType,
                                                            updateStartDateInYMD,
                                                            updateEndDateInYMD,
                                                            if (updateEndDate == "") true else false,
                                                            updateDescription
                                                        )
                                                    }
                                                } else {
                                                    if (updatePosition == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Position!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateExperienceLevel == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please add Experience Level!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateSelectedJobType == "") {
                                                        Toast
                                                            .makeText(
                                                                applicationContext,
                                                                "Please choose Job Type!",
                                                                Toast.LENGTH_LONG
                                                            )
                                                            .show()
                                                    } else if (updateStartDateInYMD == "") {
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
                                        }, false
                                    )

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
                                        }, false
                                    )
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

    //update summary
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .systemBarsPadding()
    ) {

        if (updateSummaryVisible) {
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
                                        updateSummaryVisible = false
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
                            value = updateSummary,
                            onValueChange = {
                                updateSummary = it
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
                                        if (!updateSummary.isNullOrBlank()) {
                                            scope.launch {
                                                viewModel.updateSummary(updateSummary)
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

data class Country(val name: String, val flag: String)

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
fun DateButtonWithTrailingIconForExpireDate(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit,
    isNotExpire: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            showDatePicker = if (isNotExpire) false else true
        },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = if (date.isEmpty()) label else date,
                color = if (date.isEmpty()) Color(0xFFBDBDBD) else Color(0xFF4A4A4A),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                modifier = Modifier
                    .size(18.dp)
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
fun DateButtonWithTrailingIcon(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit,
    isCurrentStudying: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            showDatePicker = true
        },
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Transparent)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = if (date.isEmpty()) label else date,
                color = if (date.isEmpty()) Color(0xFFBDBDBD) else Color(0xFF4A4A4A),
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Calendar Icon",
                modifier = Modifier
                    .size(18.dp)
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
fun DateButtonForEducationEndDate(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit,
    isPresent: Boolean
) {
    var showDatePicker by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = {
            showDatePicker = if (isPresent) false else true
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
fun DateButton(
    context: Context,
    label: String,
    date: String,
    onDateSelected: (String) -> Unit,
    onDateSelectedInYMD: (String) -> Unit,
    isCurrentStudying: Boolean
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

fun getCountries(): ArrayList<String> {
    val isoCountryCodes: Array<String> = Locale.getISOCountries()
    val countriesWithEmojis: ArrayList<String> = arrayListOf()
    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName: String = locale.displayCountry
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        countriesWithEmojis.add("$flag $countryName")
    }
    return countriesWithEmojis
}
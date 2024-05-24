package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.FileInfo
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.getFileName
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitAssignmentViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubmitAssignmentScreen(viewModel: SubmitAssignmentViewModel, navController: NavController, notiId: String,
jobId: String, referenceId: String, title: String, status: String, subDomain: String, referenceApplicationId: String) {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateFormatWithHour = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentDateWithHour = dateFormatWithHour.format(Date())

    var applicationContext = LocalContext.current.applicationContext
    var description by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val selectedFileList = remember { mutableStateListOf<SelectedFileInfo>() }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val fileName = getFileName(applicationContext, uri)
            selectedFileList.add(SelectedFileInfo(name = fileName, file = uri))
        } else {
            Log.e("FileSelectionScreen", "Selected URI is null")
        }
    }

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccessUploadMultipleFile) {
        LaunchedEffect(Unit) {
            if (uiState.multiFileList.isNotEmpty()) {
                var attachments = Json.encodeToString(uiState.multiFileList)
                Log.d("attachments>", attachments)

                if (!attachments.isNullOrBlank()) {
                    scope.launch {
                        viewModel.responseAssignment(
                            jobId,
                            referenceId,
                            title,
                            referenceId,
                            "received",
                            currentDate,
                            description,
                            currentDateWithHour,
                            attachments,
                            subDomain, referenceApplicationId
                        )
                    }
                }
            }
        }
    }

    if (uiState.isAssignmentResponseSuccess) {
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.updateNotification(
                    notiId
                )
            }
        }
    }

    if (uiState.isSuccess) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
    }

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
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Back Icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable { navController.popBackStack() },
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Submit assignment",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Description",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF6A6A6A),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(195.dp)
                .border(
                    1.dp,
                    Color(0xFFE4E7ED),
                    RoundedCornerShape(0.dp)
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
                    "Type here...",
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

        Spacer(modifier = Modifier.height(10.dp))

        AnimatedVisibility(visible = selectedFileList.isNotEmpty()) {
            FlowRow(
                maxItemsInEachRow = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                repeat(selectedFileList.size) { index ->
                    var item = selectedFileList[index]

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .background(
                                color = Color(0xFFF2F6FC),
                                shape = MaterialTheme.shapes.medium
                            )
                            .DashBorder(
                                1.dp,
                                Color(0xFFF2F6FC),
                                8.dp
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
                                    painter = painterResource(
                                        id = R.drawable.assignment_file_logo
                                    ),
                                    contentDescription = "File Logo Icon",
                                    modifier = Modifier
                                        .size(width = 15.dp, height = 16.67.dp)
                                )

                                Spacer(
                                    modifier = Modifier.width(
                                        8.dp
                                    )
                                )

                                Text(
                                    text = item.name,
                                    fontFamily = FontFamily(
                                        Font(
                                            R.font.poppins_regular
                                        )
                                    ),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF757575),
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(
                                    8.dp
                                )
                            )

                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "Close Icon",
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        selectedFileList.remove(item)
                                    }
                            )
                        }

                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Image(
            alignment = Alignment.TopStart,
            painter = painterResource(id = R.drawable.assignment_attach_file),
            contentDescription = "Assignment Attach Icon",
            modifier = Modifier
                .clickable {
                    launcher.launch("*/*")
                }
                .fillMaxWidth()
                .size(width = 107.dp, height = 24.dp)
        )

    }

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
                    var validate = (description != "" && selectedFileList.isNotEmpty())
                    if (!validate) {
                        if (description.isNullOrBlank()) {
                            Toast.makeText(applicationContext, "Please fill description!", Toast.LENGTH_LONG).show()
                        }

                        if (selectedFileList.isEmpty()) {
                            Toast.makeText(applicationContext, "Please upload Assignment File!", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        var selectedFiles: MutableList<Uri?> = ArrayList()
                        var selectedFileNames: MutableList<String?> = ArrayList()
                        var fileTypes: MutableList<String> = ArrayList()

                        selectedFiles.clear()
                        selectedFileNames.clear()
                        fileTypes.clear()

                        if (selectedFileList.isNotEmpty()) {
                            selectedFileList.map { fileInfo ->
                                fileInfo.file.let {
                                    selectedFiles.add(fileInfo.file)
                                }

                                fileInfo.name.let {
                                    selectedFileNames.add(fileInfo.name)
                                }

                                fileTypes.add("assignment")

                            }
                        }
                        scope.launch {
                            viewModel.uploadMultipleFiles(
                                selectedFiles, selectedFileNames, fileTypes, referenceId
                            )
                        }
                    }
                },
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

data class SelectedFileInfo(
    val name: String,
    val file: Uri?
)
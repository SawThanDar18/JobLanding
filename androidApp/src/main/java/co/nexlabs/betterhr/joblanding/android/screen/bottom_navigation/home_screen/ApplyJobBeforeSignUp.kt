package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.network.api.apply_job.ApplyJobViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.register.MultiStyleText
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.network.register.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ApplyJobBeforeSignUp() {
    var progressVisible by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    val coroutineScope = rememberCoroutineScope()

    var bottomBarVisible by remember { mutableStateOf(false) }
    val systemUiController = rememberSystemUiController()

    var applicationContext = LocalContext.current.applicationContext
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var jobTitle by remember { mutableStateOf("") }
    var companyName by remember { mutableStateOf("") }
    var startMonth by remember { mutableStateOf("") }
    var startYear by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val gifPainter: Painter = painterResource(R.drawable.marked)

    var step by remember { mutableStateOf("stepOne") }

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

    /*val scope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading)
    val uiStateForVerify = viewModel.uiStateForVerify.collectAsState(initial = UiState.Loading)*/
    var text by remember { mutableStateOf("") }
    var boxColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }
    var code: List<Char> by remember { mutableStateOf(listOf()) }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Button(onClick = { bottomBarVisible = true }) {
            Text(text = "Show Bottom Bar")
        }

        if (bottomBarVisible) {
            ModalBottomSheetLayout(
                sheetContent = {
                    when(step) {
                        "stepOne" -> {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(16.dp, 24.dp, 16.dp, 24.dp),
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
                                                step = "stepOne"
                                            },
                                        alignment = Alignment.Center
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                ApplyJobStateProgressBar(steps = stepOne)

                                Spacer(modifier = Modifier.height(24.dp))

                                androidx.compose.material3.Text(
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

                                    MultiStyleText(text1 = "Your phone number", color1 = Color(0xFF4A4A4A), text2 = "*", color2 = Color(0xFFffa558))
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
                                                androidx.compose.material3.Text(
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
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA7BAC5)),
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
                                            androidx.compose.material3.Text(
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
                                                    if (android.util.Patterns.PHONE.matcher(text).matches()) {
                                                        /*scope.launch {
                                                            isTimerRunning = true
                                                            viewModel.requestOTP(text)
                                                            viewModel.updatePhone(text)
                                                        }*/
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
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1ED292)),
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
                                            androidx.compose.material3.Text(
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
                                    androidx.compose.material3.Text(
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
                                    MultiStyleText(text1 = "Enter OTP Code", color1 = Color(0xFF4A4A4A), text2 = "*", color2 = Color(0xFFffa558))
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
                                                androidx.compose.material3.Text(
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
                                                textAlign = TextAlign.Center, color = Color(0xFFA7BAC5)
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
                                                            focusRequesters.getOrNull(index)?.requestFocus()
                                                        }
                                                    } else {
                                                        if (code.size > index) {
                                                            temp[index] = value.getOrNull(0) ?: ' '
                                                        } else {
                                                            temp.add(value.getOrNull(0) ?: ' ')
                                                            code = temp
                                                            focusRequesters.getOrNull(index + 1)?.requestFocus()
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

                                        androidx.compose.material3.Text(
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
                                            androidx.compose.material3.Text(
                                                text = "00:",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W400,
                                                color = Color(0xFFA7BAC5),
                                                fontSize = 12.sp,
                                                modifier = Modifier.wrapContentSize()
                                            )

                                            androidx.compose.material3.Text(
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
                                                step = "stepTwo"
                                                /*scope.launch {
                                                viewModel.verifyOTP(code.joinToString(separator = ""))
                                            }*/
                                            }
                                            .height(50.dp)
                                            .fillMaxWidth()
                                            .background(
                                                color = boxColor,
                                                shape = MaterialTheme.shapes.medium
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        androidx.compose.material3.Text(
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
                                                step = "stepOne"
                                            },
                                        alignment = Alignment.Center
                                    )
                                }

                                ApplyJobStateProgressBar(steps = stepTwo)

                                Spacer(modifier = Modifier.height(16.dp))

                                androidx.compose.material3.Text(
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
                                        Box(contentAlignment = Alignment.Center) {
                                            Image(
                                                painter = painterResource(id = R.drawable.camera),
                                                contentDescription = "Profile Icon",
                                                modifier = Modifier
                                                    .size(84.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Fit
                                            )
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
                                            placeholder = { androidx.compose.material3.Text("Enter your name", color = Color(0xFFAAAAAA)) },
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

                                        androidx.compose.material3.Text(
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
                                            placeholder = { androidx.compose.material3.Text("Enter your name", color = Color(0xFFAAAAAA)) },
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

                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                                .background(
                                                    color = Color.Transparent,
                                                    shape = MaterialTheme.shapes.medium
                                                )
                                                .DashBorder(1.dp, Color(0xFF757575), 4.dp),
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

                                        Spacer(modifier = Modifier.height(16.dp))

                                        androidx.compose.material3.Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "Files Attachments",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF4A4A4A),
                                            fontSize = 14.sp
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))

                                        LazyRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            items(0) { index ->
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
                                                                painter = painterResource(id = R.drawable.bank_logo),
                                                                contentDescription = "PDF Logo Icon",
                                                                modifier = Modifier
                                                                    .size(24.dp)
                                                            )

                                                            Spacer(modifier = Modifier.width(16.dp))

                                                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                                                androidx.compose.material3.Text(
                                                                    text = "DesignPortfolio.pdf",
                                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                                    fontWeight = FontWeight.W400,
                                                                    color = Color(0xFF4A4A4A),
                                                                    fontSize = 14.sp
                                                                )

                                                                androidx.compose.material3.Text(
                                                                    text = "5.6 MB",
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
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Center
                                            ) {
                                                Image(
                                                    painter = painterResource(id = R.drawable.attach_file),
                                                    contentDescription = "Upload Icon",
                                                    modifier = Modifier
                                                        .size(20.dp)
                                                )

                                                androidx.compose.material3.Text(
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
                                                        if (fullName.isNotBlank() && email.isNotBlank()) {
                                                            //go to work experience
                                                            step = "stepThree"
                                                        } else {
                                                            Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Please fill name & email!",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
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
                                                androidx.compose.material3.Text(
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
                                                step = "stepOne"
                                            },
                                        alignment = Alignment.Center
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                ApplyJobStateProgressBar(steps = stepThree)

                                Spacer(modifier = Modifier.height(24.dp))

                                androidx.compose.material3.Text(
                                    text = "Your Work Experience",
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W600,
                                    color = Color(0xFF6A6A6A),
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                androidx.compose.material3.Text(
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
                                        .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp))
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = jobTitle,
                                    onValueChange = {
                                        jobTitle = it
                                    },
                                    placeholder = {
                                        androidx.compose.material3.Text(
                                            "Enter your current job title",
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

                                androidx.compose.material3.Text(
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
                                        .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp))
                                        .background(
                                            color = Color.Transparent,
                                            shape = MaterialTheme.shapes.medium
                                        ),
                                    value = companyName,
                                    onValueChange = {
                                        companyName = it
                                    },
                                    placeholder = {
                                        androidx.compose.material3.Text(
                                            "Enter your current company name",
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

                                androidx.compose.material3.Text(
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
                                        value = startMonth,
                                        onValueChange = {
                                            startMonth = it
                                        },
                                        placeholder = {
                                            androidx.compose.material3.Text(
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
                                    )

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
                                            androidx.compose.material3.Text(
                                                "Start year",
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
                                                if (jobTitle.isNotBlank() && companyName.isNotBlank() && startMonth.isNotBlank() && startYear.isNotBlank()) {
                                                    //go to complete state
                                                    step = "stepFour"
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Please fill information!",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
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
                                        androidx.compose.material3.Text(
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

                                Image(
                                    painter = gifPainter,
                                    contentDescription = "GIF",
                                    modifier = Modifier
                                        .fillMaxWidth().height(200.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.height(32.dp))

                                Box(contentAlignment = Alignment.Center) {
                                    androidx.compose.material3.Text(
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
                                        androidx.compose.material3.Text(
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
                sheetShape = RoundedCornerShape(bottomStart = 0.dp, bottomEnd = 0.dp, topStart = 24.dp, topEnd = 24.dp),
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
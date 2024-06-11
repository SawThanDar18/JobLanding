package co.nexlabs.betterhr.joblanding.android.screen.register

import android.opengl.Visibility
import android.util.Log
import android.view.View
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen.MyToast
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import co.nexlabs.betterhr.joblanding.network.register.UiState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel) {

    var timer by remember { mutableStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val uiState = viewModel.uiState.collectAsState(initial = UiState.Loading)
    val uiStateForVerify = viewModel.uiStateForVerify.collectAsState(initial = UiState.Loading)
    val keyboardController = LocalSoftwareKeyboardController.current
    val applicationContext = LocalContext.current.applicationContext
    var text by remember { mutableStateOf("") }

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

    when (val currentState = uiState.value) {
        is UiState.Loading -> {

        }

        is UiState.Success -> {
            isTimerRunning = true
        }

        is UiState.Error -> {
            MyToast(currentState.errorMessage)
        }
    }

    if (timer == 0) {
        isTimerRunning = false
    } else {
        timerText = timer.toString()
    }

    when (val currentState = uiStateForVerify.value) {
        is UiState.Loading -> {
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                scope.launch {
                    viewModel.updateToken(currentState.data)
                }
                navController.navigate("profile-register-screen")
            }
        }

        is UiState.Error -> {
            MyToast(currentState.errorMessage)
        }
    }

    var boxColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }

    var code: List<Char> by remember { mutableStateOf(listOf()) }

    Column(
        modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Box(contentAlignment = Alignment.CenterStart,
            modifier = Modifier.clickable {
                scope.launch {
                    if (viewModel.getPageId().isNotBlank()) {
                        navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"profile"}")
                    }
                }
            }) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Arrow Left Icon",
                modifier = Modifier
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Register",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your phone number*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
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
                    .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
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
                    placeholder = { Text("Enter your number", color = Color(0xFFAAAAAA)) },
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
                        .background(color = Color(0xFFA7BAC5), shape = MaterialTheme.shapes.medium)
                        .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp))
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
                            if (android.util.Patterns.PHONE.matcher(text).matches()) {
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
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1ED292)),
                    modifier = Modifier
                        .weight(1f)
                        .width(83.dp)
                        .height(50.dp)
                        .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium)
                        .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(0.dp, 4.dp, 4.dp, 0.dp))
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
            Text(
                text = "Enter OTP Code*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
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
                        .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp)),
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
                    painter = painterResource(id = R.drawable.alert_circle),
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

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 32.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Already have an account?",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "log in",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF1ED292),
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {
                        navController.navigate("login-screen")
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

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
                    .background(color = boxColor, shape = MaterialTheme.shapes.medium),
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


@Composable
fun Content(data: String) {
    // UI content
    Text(text = data)
}

@Composable
fun ErrorView(error: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = error)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Retry API call */ }) {
            Text("Retry")
        }
    }
}

@Composable
fun MyToast(message: String) {
    Toast.makeText(LocalContext.current.applicationContext, message, Toast.LENGTH_SHORT).show()
    /*var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(isVisible) {
        delay(durationMillis)
        isVisible = false
    }

    if (isVisible) {
        Surface(
            color = Color.Black.copy(alpha = 0.8f),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = message,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }*/
}

fun clearFocus(rootView: View) {
    rootView.clearFocus()
}



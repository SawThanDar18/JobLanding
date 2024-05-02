package co.nexlabs.betterhr.joblanding.android.screen.register

import android.util.Log
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
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.register.ProfileRegisterViewModel
import co.nexlabs.betterhr.joblanding.network.register.UiState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModel

@Composable
fun ProfileRegisterScreen(viewModel: ProfileRegisterViewModel, navController: NavController) {
    var applicationContext = LocalContext.current.applicationContext
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccessForCandidateId) {
        scope.launch {
            viewModel.updateCandidateId(uiState.candidateId)
            viewModel.getBearerToken(viewModel.getToken())
        }
    }

    if (uiState.isSuccessForBearerToken) {
        scope.launch {
            viewModel.updateBearerToken(uiState.bearerToken)
            if (viewModel.getPageId().isNotBlank()) {
                navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"profile"}")
            }
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

        Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 70.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                Box {
                    Image(
                        painter = painterResource(id = R.drawable.arrow_left),
                        contentDescription = "Arrow Left Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Box {
                    Text(
                        text = "Register",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 32.sp
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = "Camera Icon",
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
                                .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = fullName,
                            onValueChange = {
                                fullName = it
                            },
                            placeholder = { Text("Enter your name", color = Color(0xFFAAAAAA)) },
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
                                .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp))
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                ),
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            placeholder = { Text("Enter your name", color = Color(0xFFAAAAAA)) },
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
                                //.border(1.dp, Color(0xFF757575), RoundedCornerShape(4.dp))
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

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Files Attachments",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyRow(
                            //maxItemsInEachRow = 1,
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
                                                Text(
                                                    text = "DesignPortfolio.pdf",
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    fontWeight = FontWeight.W400,
                                                    color = Color(0xFF4A4A4A),
                                                    fontSize = 14.sp
                                                )

                                                Text(
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
                                    painter = painterResource(id = R.drawable.attach_file),
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

                    }
                }
            }
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
                .clickable {
                    if (fullName.isNotBlank() && email.isNotBlank()) {
                        scope.launch {
                            viewModel.createCandidate(
                                fullName, email, "Android Developer", "summary"
                            )
                        }
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
                .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Register",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp,
            )
        }
    }
}


@Composable
fun MultiStyleText(text1: String, color1: Color, text2: String, color2: Color) {
    Text(
        buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = color1,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp
                )) {
                    append(text1)
                }
                withStyle(style = SpanStyle(
                    color = color2,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp
                )) {
                    append(text2)
                }
            }
    )
}

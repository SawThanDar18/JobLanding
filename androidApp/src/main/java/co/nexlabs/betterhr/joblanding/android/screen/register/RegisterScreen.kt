package co.nexlabs.betterhr.joblanding.android.screen.register

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import co.nexlabs.betterhr.joblanding.android.R

@Composable
fun RegisterScreen(navController: NavController) {

    var boxColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }
    val keyboardController = LocalSoftwareKeyboardController.current

    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(6) {
            temp.add(FocusRequester())
        }
        temp
    }

    Column(
        modifier = Modifier.padding(16.dp, 38.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Arrow Left Icon",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.navigate("profile-unregister-screen") },
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
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
            androidx.compose.material3.Text(
                text = "Your phone number*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithCornerColor()

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
            androidx.compose.material3.Text(
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
                                    focusRequesters.getOrNull(index - 1)?.requestFocus()
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
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.alert_circle_outline),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.width(1.dp))
            androidx.compose.material3.Text(
                text = "Get instead OTP code",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFFAAAAAA),
                fontSize = 12.sp,
                style = TextStyle(textAlign = TextAlign.Justify),
                modifier = Modifier.fillMaxWidth()
            )


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
                androidx.compose.material3.Text(
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
                androidx.compose.material3.Text(
                    text = "log in",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF1ED292),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(color = boxColor, shape = MaterialTheme.shapes.medium),
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

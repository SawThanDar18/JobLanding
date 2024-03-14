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
import co.nexlabs.betterhr.joblanding.android.theme.colorBlackGrey
import co.nexlabs.betterhr.joblanding.android.theme.colorBlue
import co.nexlabs.betterhr.joblanding.android.theme.colorBlueGrey
import co.nexlabs.betterhr.joblanding.android.theme.colorGreen
import co.nexlabs.betterhr.joblanding.android.theme.colorGrey
import co.nexlabs.betterhr.joblanding.android.theme.colorWhite
import co.nexlabs.betterhr.joblanding.android.theme.colorWhiteGrey
import co.nexlabs.betterhr.joblanding.android.theme.dp_0
import co.nexlabs.betterhr.joblanding.android.theme.dp_1
import co.nexlabs.betterhr.joblanding.android.theme.dp_16
import co.nexlabs.betterhr.joblanding.android.theme.dp_20
import co.nexlabs.betterhr.joblanding.android.theme.dp_24
import co.nexlabs.betterhr.joblanding.android.theme.dp_32
import co.nexlabs.betterhr.joblanding.android.theme.dp_38
import co.nexlabs.betterhr.joblanding.android.theme.dp_4
import co.nexlabs.betterhr.joblanding.android.theme.dp_40
import co.nexlabs.betterhr.joblanding.android.theme.dp_50
import co.nexlabs.betterhr.joblanding.android.theme.dp_8
import co.nexlabs.betterhr.joblanding.android.theme.fontWeight_400
import co.nexlabs.betterhr.joblanding.android.theme.fontWeight_600
import co.nexlabs.betterhr.joblanding.android.theme.sp_12
import co.nexlabs.betterhr.joblanding.android.theme.sp_14
import co.nexlabs.betterhr.joblanding.android.theme.sp_32
import co.nexlabs.betterhr.joblanding.android.theme.unRegisterScreen

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
        modifier = Modifier.padding(dp_16, dp_38, dp_16, dp_0),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Arrow Left Icon",
                modifier = Modifier
                    .size(dp_24)
                    .clickable { navController.navigate(unRegisterScreen) },
            )
        }

        Spacer(modifier = Modifier.height(dp_40))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Register",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_600,
                color = colorGrey,
                fontSize = sp_32
            )
        }

        Spacer(modifier = Modifier.height(dp_20))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Your phone number*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_600,
                color = colorBlackGrey,
                fontSize = sp_14
            )
        }

        Spacer(modifier = Modifier.height(dp_8))

        TextFieldWithCornerColor()

        Spacer(modifier = Modifier.height(dp_8))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "lost your phone number?",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_400,
                color = colorBlue,
                fontSize = sp_12
            )
        }

        Spacer(modifier = Modifier.height(dp_20))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Enter OTP Code*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_400,
                color = colorBlackGrey,
                fontSize = sp_14
            )
        }

        Spacer(modifier = Modifier.height(dp_8))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            (0 until 6).forEach { index ->
                OutlinedTextField(
                    modifier = Modifier
                        .size(dp_50)
                        .focusRequester(focusRequesters[index])
                        .border(1.dp, colorBlueGrey, RoundedCornerShape(dp_4)),
                    placeholder = {
                        androidx.compose.material3.Text(
                            "—",
                            color = colorWhiteGrey,
                            style = TextStyle(textAlign = TextAlign.Center),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorWhiteGrey,
                        backgroundColor = Color.Transparent,
                        cursorColor = colorWhiteGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = androidx.compose.material.MaterialTheme.typography.body2.copy(
                        textAlign = TextAlign.Center, color = colorBlueGrey
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
                            boxColor = colorGreen
                            keyboardController?.hide()
                        }
                    ),
                    //visualTransformation = PasswordVisualTransformation()
                )

            }
        }

        Spacer(modifier = Modifier.height(dp_8))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(id = R.drawable.alert_circle_outline),
                contentDescription = null,
                modifier = Modifier.size(dp_16),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.width(dp_1))
            androidx.compose.material3.Text(
                text = "Get instead OTP code",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_400,
                color = colorWhiteGrey,
                fontSize = sp_12,
                style = TextStyle(textAlign = TextAlign.Justify),
                modifier = Modifier.fillMaxWidth()
            )


        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(dp_0, dp_32)
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
                    fontWeight = fontWeight_400,
                    color = colorGrey,
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
                    color = colorGreen,
                    fontSize = sp_14
                )
            }
            Spacer(modifier = Modifier.height(dp_16))
            Box(
                modifier = Modifier
                    .height(dp_50)
                    .fillMaxWidth()
                    .background(color = boxColor, shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Next",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = fontWeight_600,
                    color = colorWhite,
                    fontSize = sp_14
                )
            }

        }

    }
}

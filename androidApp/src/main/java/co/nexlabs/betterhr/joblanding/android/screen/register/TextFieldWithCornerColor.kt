package co.nexlabs.betterhr.joblanding.android.screen.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.ResponseMessage
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil
import io.michaelrocks.libphonenumber.android.Phonenumber
import kotlinx.coroutines.launch

@Composable
fun TextFieldWifthCornerColor(viewModel: RegisterViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val applicationContext = LocalContext.current.applicationContext

    val scope = rememberCoroutineScope()

    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
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

        Button(
            onClick = {
                if (text.isNotEmpty()) {
                    if (android.util.Patterns.PHONE.matcher(text).matches()) {
                        scope.launch {
                            viewModel.requestOTP(text)
                        }
                    } else {
                        Toast.makeText(applicationContext, "Phone Number is invalid..", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1ED292)),
            modifier = Modifier
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

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    if (phoneNumber.isEmpty()) return true
    return phoneNumber.matches(Regex("^\\+?\\d{6,15}$"))
}

class PhoneNumberMask : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        val raw = text.text
        for (i in raw.indices) {
            if (i % 3 == 0 && i != 0) {
                out += "-"
            }
            out += raw[i]
        }
        return TransformedText(AnnotatedString(out), OffsetMapping.Identity)
    }
}


fun formatPhoneNumber(context: Context, phoneNumber: String, countryCode: String): String {
    val phoneNumberUtil = PhoneNumberUtil.createInstance(context)
    try {
        val numberProto: Phonenumber.PhoneNumber = phoneNumberUtil.parse(phoneNumber, countryCode)
        if (!phoneNumberUtil.isValidNumber(numberProto)) {
            return "Invalid phone number"
        }
        return phoneNumberUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error formatting phone number"
    }
}

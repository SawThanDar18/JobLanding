package co.nexlabs.betterhr.joblanding.android.screen.register

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.theme.colorBlueGrey
import co.nexlabs.betterhr.joblanding.android.theme.colorGreen
import co.nexlabs.betterhr.joblanding.android.theme.colorWhite
import co.nexlabs.betterhr.joblanding.android.theme.colorWhiteGrey
import co.nexlabs.betterhr.joblanding.android.theme.dp_0
import co.nexlabs.betterhr.joblanding.android.theme.dp_1
import co.nexlabs.betterhr.joblanding.android.theme.dp_4
import co.nexlabs.betterhr.joblanding.android.theme.dp_50
import co.nexlabs.betterhr.joblanding.android.theme.fontWeight_400
import co.nexlabs.betterhr.joblanding.android.theme.sp_12
import co.nexlabs.betterhr.joblanding.android.theme.sp_14

@Composable
fun TextFieldWithCornerColor() {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(dp_50)
                .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .border(dp_1, colorBlueGrey, RoundedCornerShape(dp_4, dp_0, dp_0, dp_4)),
                placeholder = { Text("Enter your number", color = colorWhiteGrey) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorWhiteGrey,
                    backgroundColor = Color.Transparent,
                    cursorColor = colorGreen,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontWeight = fontWeight_400,
                    fontSize = sp_14,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = colorWhiteGrey
                ),
                visualTransformation = PhoneNumberMask(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle Done action if needed
                    }
                ),
            )
        }

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorGreen),
            modifier = Modifier
                .height(dp_50)
                .background(color = colorGreen, shape = MaterialTheme.shapes.medium)
                .border(dp_1, colorGreen, RoundedCornerShape(dp_0, dp_4, dp_4, dp_0))
        )
        {
            Text(
                text = "Send OTP",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_400,
                color = colorWhite,
                fontSize = sp_12
            )
        }
    }
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

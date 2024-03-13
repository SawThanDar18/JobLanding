package co.nexlabs.betterhr.joblanding.android

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFieldWithCornerColor() {
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
                onValueChange = { text = it },
                modifier = Modifier
                    .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp, 0.dp, 0.dp, 4.dp)),
                placeholder = { Text("Enter your number") },
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
                visualTransformation = PhoneNumberMask(),
            )
        }

        Box(
            modifier = Modifier
                .height(50.dp)
                .background(color = Color(0xFF1ED292), shape = MaterialTheme.shapes.extraSmall),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Send OTP",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFFFFFFFF),
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp, 0.dp).border(0.dp, Color(0xFF1ED292), RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp))
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

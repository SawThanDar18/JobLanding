package co.nexlabs.betterhr.joblanding.android.screen.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PhoneNumberInput() {
    var phoneNumber by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle Done action if needed
                }
            ),
            //visualTransformation = PhoneNumberMask(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/*class PhoneNumberMask : VisualTransformation {
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
}*/



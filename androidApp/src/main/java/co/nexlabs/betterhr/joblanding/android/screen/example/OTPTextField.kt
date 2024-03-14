package co.nexlabs.betterhr.joblanding.android.screen.example

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    length: Int,
    onFilled: (code: String) -> Unit
) {

    var boxColor by remember { mutableStateOf(Color(0xFFD9D9D9)) }
    val keyboardController = LocalSoftwareKeyboardController.current

    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        (0 until length).forEach { index ->
            OutlinedTextField(
                modifier = Modifier.size(50.dp)
                    .focusRequester(focusRequesters[index])
                    .border(1.dp, Color(0xFFA7BAC5), RoundedCornerShape(4.dp, 0.dp, 0.dp, 4.dp)),
                placeholder = { Text("-", color = Color(0xFFAAAAAA)) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0xFFAAAAAA),
                    backgroundColor = Color.White,
                    cursorColor = Color(0xFFAAAAAA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.body2.copy(
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
                                temp[index] = value.getOrNull(0)?: ' '
                            } else {
                                temp.add(value.getOrNull(0) ?: ' ')
                                code = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus() ?: onFilled(
                                    code.joinToString(separator = "")
                                )
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
}

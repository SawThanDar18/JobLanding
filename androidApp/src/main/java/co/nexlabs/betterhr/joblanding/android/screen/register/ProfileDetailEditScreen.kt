package co.nexlabs.betterhr.joblanding.android.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
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
import co.nexlabs.betterhr.joblanding.android.R

@Composable
fun ProfileDetailEditScreen() {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        //horizontalAlignment = Alignment.Start,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Edit Profile",
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
                    .size(24.dp),
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.size(68.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Overlap(
                modifier = Modifier.wrapContentSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bank_logo),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillWidth
                )

                Image(
                    painter = painterResource(id = R.drawable.edit_camera),
                    contentDescription = "Edit Camera Logo",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Name",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF4A4A4A),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = { Text("Saw Thandar", color = Color(0xFF4A4A4A)) },
            textStyle = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = Color(0xFF4A4A4A)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF4A4A4A),
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
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Position Description",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF4A4A4A),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            value = position,
            onValueChange = {
                position = it
            },
            placeholder = { Text("Android Developer", color = Color(0xFF4A4A4A)) },
            textStyle = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = Color(0xFF4A4A4A)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF4A4A4A),
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
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Phone number",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF4A4A4A),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
            },
            placeholder = { Text("+959400031542", color = Color(0xFF4A4A4A)) },
            textStyle = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = Color(0xFF4A4A4A)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF4A4A4A),
                backgroundColor = Color.Transparent,
                cursorColor = Color(0xFF1ED292),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
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
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Email",
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontWeight = FontWeight.W400,
            color = Color(0xFF4A4A4A),
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(4.dp))
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text("sawthandar@better.hr", color = Color(0xFF4A4A4A)) },
            textStyle = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                color = Color(0xFF4A4A4A)
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xFF4A4A4A),
                backgroundColor = Color.Transparent,
                cursorColor = Color(0xFF1ED292),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
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
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Icon",
                    modifier = Modifier
                        .size(16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.warning),
                contentDescription = "Warning Icon",
                modifier = Modifier
                    .size(13.33.dp)
            )

            Text(
                modifier = Modifier.drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = Color(0xFFEE4744),
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                },
                textAlign = TextAlign.Start,
                text = "Verify Email",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFFEE4744),
                fontSize = 12.sp
            )
        }


    }
}

@Composable
fun Overlap(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content,
    ) { measurables, constraints ->
        val largeBox = measurables[0]
        val smallBox = measurables[1]
        val looseConstraints = constraints.copy(
            minWidth = 0,
            minHeight = 0,
        )
        val largePlaceable = largeBox.measure(looseConstraints)
        val smallPlaceable = smallBox.measure(looseConstraints)
        layout(
            width = constraints.maxWidth,
            height = largePlaceable.height + smallPlaceable.height / 2,
        ) {
            largePlaceable.placeRelative(
                x = 0,
                y = 0,
            )
            smallPlaceable.placeRelative(
                x = 115,
                y = largePlaceable.height - smallPlaceable.height
            )
        }
    }
}
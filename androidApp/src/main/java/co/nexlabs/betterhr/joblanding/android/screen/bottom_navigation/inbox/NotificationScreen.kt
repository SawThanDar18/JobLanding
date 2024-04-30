package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NotificationScreen() {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.padding(16.dp, 16.dp, 0.dp, 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Inbox",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF6A6A6A),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFE4E7ED),
                            shape = MaterialTheme.shapes.medium
                        )
                        .width(29.dp)
                        .height(21.dp)
                        .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(100.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "5",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFFAAAAAA),
                        fontSize = 14.sp
                    )
                }

            }

            Image(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter Icon",
                modifier = Modifier
                    .size(20.dp, 18.dp),
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .padding(end = 16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp))
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.medium
                ),
            value = searchText,
            onValueChange = {
                searchText = it
            },
            placeholder = { Text("Search", color = Color(0xFFAAAAAA)) },
            textStyle = TextStyle(
                fontWeight = FontWeight.W400,
                fontSize = 13.sp,
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
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search Icon",
                    modifier = Modifier
                        .size(16.dp)
                )
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp, bottom = 32.dp)
        ) {

            item {
                FlowRow(
                    maxItemsInEachRow = 1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    repeat(20) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.wrapContentSize(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.Top
                            ) {

                                Box(
                                    modifier = Modifier
                                        .width(4.dp)
                                        .height(64.dp)
                                        .border(1.dp, Color(0xFFF8CB2E), RoundedCornerShape(100.dp))
                                        .background(
                                            color = Color(0xFFF8CB2E),
                                            shape = MaterialTheme.shapes.medium
                                        )
                                ) {}

                                Column(
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(64.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        modifier = Modifier.width(234.dp),
                                        text = "Congrats! You’ve got an employment letter",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W500,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 14.sp,
                                        maxLines = 2,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        modifier = Modifier.width(47.dp),
                                        text = "5:00 PM",
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W500,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 12.sp,
                                        maxLines = 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                            }

                            Row(
                                modifier = Modifier.wrapContentSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.bank_logo),
                                    contentDescription = "Company Icon",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.chevron_right),
                                    contentDescription = "Arrow Icon",
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}
package co.nexlabs.betterhr.joblanding.android.screen.register

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.theme.DashBorder

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CompleteProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 16.dp, 16.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Profile",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setting Icon",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        //showMenu = true
                    },
                alignment = Alignment.Center
            )
        }

        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.company_logo),
                        contentDescription = "Edit Camera Logo",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(
                        modifier = Modifier.height(58.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Saw Thandar",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp
                        )

                        Text(
                            text = "Android Developer",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF6A6A6A),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "edit profile",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 12.sp
                            )

                            Image(
                                painter = painterResource(id = R.drawable.chevron_right),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.phone_icon),
                        contentDescription = "Phone Icon",
                        modifier = Modifier
                            .size(20.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "09984849984",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF6A6A6A),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.emil_icon),
                        contentDescription = "Email Icon",
                        modifier = Modifier
                            .size(20.dp),
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "sawthandar@better.hr",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W400,
                        color = Color(0xFF6A6A6A),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(30.dp)
                        .border(1.dp, Color(0xFFFDEDEC), RoundedCornerShape(4.dp))
                        .background(color = Color(0xFFFDEDEC), shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.warning),
                            contentDescription = "Warning Icon",
                            modifier = Modifier
                                .size(13.33.dp),
                            alignment = Alignment.Center
                        )

                        Text(
                            text = " Need Verification. ",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFEE4744),
                            fontSize = 12.sp
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
                            text = "Click here to verify your email.",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFFEE4744),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(52.dp)
                        .border(1.dp, Color(0xFFFEF9E6), RoundedCornerShape(4.dp))
                        .background(color = Color(0xFFFEF9E6), shape = MaterialTheme.shapes.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.alert_circle),
                            contentDescription = "Alert Icon",
                            modifier = Modifier
                                .size(20.dp),
                            alignment = Alignment.Center
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Please complete your profile!",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W400,
                            color = Color(0xFF757575),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    MultiStyleTextForCompleteProfile(
                        text1 = "Resume or CV ",
                        color1 = Color(0xFF6A6A6A),
                        text2 = "*",
                        color2 = Color(0xFFEE4744)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(200.dp)
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                        .DashBorder(1.dp, Color(0xFF757575), 4.dp),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.x),
                                contentDescription = "X Icon",
                                modifier = Modifier
                                    .size(14.dp),
                                alignment = Alignment.CenterEnd
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.pdf_file_icon),
                                contentDescription = "Attach File Icon",
                                modifier = Modifier
                                    .size(width = 39.08.dp, height = 48.dp),
                                alignment = Alignment.Center
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                maxLines = 2,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                text = "James’s\nResume1.pdf",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .width(114.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Files Attachments",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF6A6A6A),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    maxItemsInEachRow = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
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

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(40.dp)
                        .border(1.dp, Color(0xFF1ED292), RoundedCornerShape(8.dp))
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium),
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

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 72.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                /*Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Summary",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Experience",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Education",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Language",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Other Skills",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )

                Row(
                    modifier = Modifier.fillMaxWidth().height(72.dp).padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Certifications",
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontWeight = FontWeight.W600,
                        color = Color(0xFF6A6A6A),
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        painter = painterResource(id = R.drawable.x),
                        contentDescription = "Plus Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )

                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(bottom = 72.dp)
                        .height(2.dp)
                        .background(color = Color(0xFFE4E7ED))
                )*/

            }
        }
    }
}

@Composable
fun MultiStyleTextForCompleteProfile(text1: String, color1: Color, text2: String, color2: Color) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = color1,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
            ) {
                append(text1)
            }
            withStyle(
                style = SpanStyle(
                    color = color2,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
            ) {
                append(text2)
            }
        }
    )
}
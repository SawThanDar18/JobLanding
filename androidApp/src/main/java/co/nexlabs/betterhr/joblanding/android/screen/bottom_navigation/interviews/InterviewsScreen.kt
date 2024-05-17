package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.interviews

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InterviewsScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp, 16.dp, 80.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Interviews",
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
                        text = "3",
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

        LazyColumn {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Today",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF4A4A4A),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                FlowRow(
                    maxItemsInEachRow = 1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(1) { index ->
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Monday, 18 July",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6A6A6A),
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(89.dp)
                                    .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
                                    .background(
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "02:45 PM - 04:00 PM",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF757575),
                                            fontSize = 12.sp
                                        )

                                        Text(
                                            text = "1st Interview",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W500,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 14.sp
                                        )

                                        Box(
                                            modifier = Modifier
                                                .width(73.dp)
                                                .height(29.dp)
                                                .border(1.dp, Color(0xFFF2FDF9), RoundedCornerShape(8.dp))
                                                .background(
                                                    color = Color(0xFFF2FDF9),
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "In person",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W600,
                                                color = Color(0xFF1ED292),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Image(
                                        painter = painterResource(id = R.drawable.bank_logo),
                                        contentDescription = "Company Icon",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                    )
                                }

                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Upcoming",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF4A4A4A),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                FlowRow(
                    maxItemsInEachRow = 1,
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
                        Column(
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Monday, 18 July",
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6A6A6A),
                                fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(89.dp)
                                    .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
                                    .background(
                                        color = Color.Transparent,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            text = "02:45 PM - 04:00 PM",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W400,
                                            color = Color(0xFF757575),
                                            fontSize = 12.sp
                                        )

                                        Text(
                                            text = "2nd Interview",
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                            fontWeight = FontWeight.W500,
                                            color = Color(0xFF6A6A6A),
                                            fontSize = 14.sp
                                        )

                                        Box(
                                            modifier = Modifier
                                                .width(73.dp)
                                                .height(29.dp)
                                                .border(1.dp, Color(0xFFF2FDF9), RoundedCornerShape(8.dp))
                                                .background(
                                                    color = Color(0xFFF2FDF9),
                                                    shape = MaterialTheme.shapes.medium
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Virtual",
                                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                fontWeight = FontWeight.W600,
                                                color = Color(0xFF1ED292),
                                                fontSize = 12.sp
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Image(
                                        painter = painterResource(id = R.drawable.bank_logo),
                                        contentDescription = "Company Icon",
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }

    }
}
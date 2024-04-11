package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.R

@Composable
fun CollectionListsDetail(navController: NavController) {

    val items = (0..4).toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Arrow Left",
                modifier = Modifier.size(24.dp),
            )

            Text(
                text = "Recent Jobs",
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp,
            )

            Image(
                painter = painterResource(id = R.drawable.search_black),
                contentDescription = "Search",
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.cover),
                contentDescription = "Cover",
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp)
        ) {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                        .width(328.dp)
                        .height(80.dp)
                        .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.company_logo),
                                contentDescription = "Company Logo",
                                modifier = Modifier
                                    .size(48.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(
                                text = "Designer",
                                maxLines = 2,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W600,
                                color = Color(0xFF6A6A6A),
                                fontSize = 13.sp,
                            )

                            Text(
                                text = "Yoma Bank",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 12.sp,
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "MMK 400k-600k",
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF757575),
                                    fontSize = 12.sp,
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Image(
                                    painter = painterResource(id = R.drawable.grey_line),
                                    contentDescription = "Grey Space Line",
                                    modifier = Modifier
                                        .size(1.dp, 8.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = "Yangon",
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W400,
                                    color = Color(0xFF757575),
                                    fontSize = 12.sp,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.wrapContentHeight()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.save_unselected_icon),
                                contentDescription = "Save Unselected Icon",
                                modifier = Modifier
                                    .size(11.dp, 15.dp),
                                contentScale = ContentScale.Fit
                            )

                            Text(
                                text = "",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFFF8CB2E),
                                fontSize = 10.sp,
                            )

                            Text(
                                text = "2 days left",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFFF8CB2E),
                                fontSize = 10.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

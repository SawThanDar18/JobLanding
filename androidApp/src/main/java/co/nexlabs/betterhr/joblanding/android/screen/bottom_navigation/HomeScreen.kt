package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation

import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSelectionMode

@Preview
@Composable
fun HomeScreen() {

    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val items = (0..4).toList()

    var style by remember { mutableStateOf("style-1") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 65.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .height(32.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_screen_header),
                contentDescription = "Home Screen Header Logo",
                modifier = Modifier
                    .fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                .height(50.dp)
                .fillMaxWidth()
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Arrow Down",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Search jobs, companies...",
                    modifier = Modifier.padding(start = 8.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 14.sp,
                )
            }
        }

        NestedLazyColumn(style, items)
    }
}

@Composable
fun StyleSixCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(156.dp)
            .border(0.dp, Color.Transparent, RoundedCornerShape(8.dp)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.campaign),
            contentDescription = "Campaigns",
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun StyleFiveCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(156.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bank_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(42.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Mi",
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.save_unselected_icon),
                        contentDescription = "Save Unselected Icon",
                        modifier = Modifier
                            .size(9.dp, 12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Graphic Designer",
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Full time",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "MMK 300K-400K",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "Yangon, Myanmar",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun StyleFourCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(68.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(48.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.padding(start = 2.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Yoma strategic holdings Ltd.",
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF454545),
                    fontSize = 12.sp,
                )

                Text(
                    text = "8 opening",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W300,
                    color = Color(0xFF454545),
                    fontSize = 10.sp,
                )
            }
        }
    }
}

@Composable
fun StyleThreeCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(142.dp)
            .height(173.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    maxLines = 2,
                    text = "Myanmar Now",
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                    modifier = Modifier.width(60.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.company_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Front-end Developer",
                    maxLines = 2,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp,
                )
            }

            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Full time",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "MMK 300K-400K",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )

                Text(
                    text = "Yangon, Myanmar",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )
            }
        }
    }
}


@Composable
fun StyleTwoCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(156.dp)
            .height(71.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                //verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "UI Designer",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFAAAAAA),
                    fontSize = 12.sp,
                )

                Text(
                    text = "Alibaba",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFFAAAAAA),
                    fontSize = 12.sp,
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.company_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(42.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun StyleOneCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
            .width(259.dp)
            .height(119.dp)
            .border(1.dp, Color(0xFFE1E1E1), RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bank_logo),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .size(32.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Yoma strategic holding Ltd.",
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W500,
                    color = Color(0xFF6A6A6A),
                    fontSize = 12.sp,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.save_unselected_icon),
                        contentDescription = "Save Unselected Icon",
                        modifier = Modifier
                            .size(9.dp, 12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 16.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Product Designers",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFAAAAAA),
                    fontSize = 14.sp,
                )
            }

            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.salary_icon),
                    contentDescription = "Salary",
                    modifier = Modifier
                        .size(16.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "300k - 400k",
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 13.sp,
                )

                Text(
                    text = "Yangon, Myanmar",
                    modifier = Modifier.padding(start = 4.dp),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF757575),
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
fun CollectionLabelLayoutItem(index: String) {

    Row(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 14.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gradient_line),
                contentDescription = "Gradient Line",
                modifier = Modifier
                    .size(4.dp, 18.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Recent jobs",
                modifier = Modifier.padding(start = 4.dp),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 14.sp,
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See all $index jobs",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W500,
                color = Color(0xFF1ED292),
                fontSize = 13.sp,
            )
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Right Arrow",
                modifier = Modifier
                    .size(20.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun NestedLazyColumn(style: String, items: List<Int>) {
    LazyColumn {
        items(items.size) { index ->
            CollectionLabelLayoutItem(index = index.toString())
            //StyleOneLazyLayout()
            //StyleTwoLazyLayout()
            //StyleThreeLazyLayout()
            //StyleFourLazyLayout()
            //StyleFiveLazyLayout()
            StyleSixLazyLayout()
        }
    }
}

@Composable
fun StyleOneLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { subIndex ->
            StyleOneCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleTwoLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { subIndex ->
            StyleTwoCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleThreeLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(2) { subIndex ->
            StyleThreeCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleFourLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(3) { subIndex ->
            StyleFourCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleFiveLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(5) { subIndex ->
            StyleFiveCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleSixLazyLayout() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(10) { subIndex ->
            StyleSixCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

/*Row(
            //horizontalArrangement = Arrangement.Start,
            //verticalAlignment = Alignment.CenterVertically,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.Transparent, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .height(50.dp)
                .padding(16.dp, 0.dp)
                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp)),
        ) {
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search",
                modifier = Modifier
                    .padding(8.dp, 0.dp)
                    .size(24.dp),
                contentScale = ContentScale.Fit
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(
                        "Search job, companies...",
                        color = Color(0xFFAAAAAA)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0xFFAAAAAA),
                    backgroundColor = Color.Transparent,
                    cursorColor = Color(0xFF1ED292),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFFAAAAAA)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        // Handle Done action if needed
                    }
                ),
            )
        }*/

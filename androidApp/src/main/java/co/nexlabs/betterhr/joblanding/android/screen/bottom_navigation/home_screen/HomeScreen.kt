package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController, pageId: String) {

    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()
    scope.launch {
        if (pageId != null && pageId != "") {
            viewModel.getJobLandingSections(pageId)
        }

        if (viewModel.jobLandingSectionList.isNotEmpty()) {
            Log.d("list>>", viewModel.jobLandingSectionList.size.toString())
            viewModel.jobLandingSectionList.map {
                Log.d("list>>", it.title)
            }
        }
    }

    val items = (0..4).toList()

    var style by remember { mutableStateOf("style-7") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 16.dp, 65.dp),
        horizontalAlignment = Alignment.Start
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(id = R.drawable.better_icon),
                contentDescription = "Home Screen Header Logo",
                modifier = Modifier
                    .width(26.dp)
                    .height(32.dp),
            )

            Text(
                text = "Better Job",
                modifier = Modifier.padding(start = 8.dp),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF757575),
                fontSize = 24.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        NestedLazyColumn(style, items, navController)
    }
}

@Composable
fun StyleSevenCollectionListLayoutItem(item: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color.Transparent,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth()
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
                    modifier = Modifier.padding(top = 3.dp),
                    text = "Yoma Bank",
                    maxLines = 2,
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
            modifier = Modifier
                .fillMaxSize()
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
fun NestedLazyColumn(style: String, items: List<Int>, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
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
                        .fillMaxSize()
                        .clickable { navController.navigate("company-lists-detail") },
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
        }

        items(items.size) { index ->
            CollectionLabelLayoutItem(index = index.toString())
            when (style) {
                "style-1" -> StyleOneLazyLayout()
                "style-2" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleTwoLazyLayout()
                }
                "style-3" -> StyleThreeLazyLayout()
                "style-4" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleFourLazyLayout()
                }
                "style-5" -> StyleFiveLazyLayout()
                "style-6" -> StyleSixLazyLayout()
                "style-7" -> Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    StyleSevenLazyLayout()
                }
            }
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
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(6) { subIndex ->
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
        items(5) { subIndex ->
            StyleThreeCollectionListLayoutItem(item = subIndex.toString())
        }
    }
}

@Composable
fun StyleFourLazyLayout() {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(4) { subIndex ->
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

@Composable
fun StyleSevenLazyLayout() {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(10) { subIndex ->
            StyleSevenCollectionListLayoutItem(item = subIndex.toString())
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

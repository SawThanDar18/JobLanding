package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.common.ErrorLayout
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionCompaniesViewModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import kotlinx.coroutines.launch

@Composable
fun CollectionCompaniesListsScreen(
    viewModel: CollectionCompaniesViewModel,
    navController: NavController,
    collectionId: String,
    collectionName: String
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    scope.launch {
        viewModel.getCollectionCompanies(collectionId, false)
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedVisibility(
            uiState.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CircularProgressIndicator(
                color = Color(0xFF1ED292)
            )
        }

        AnimatedVisibility(
            uiState.error != UIErrorType.Nothing,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ErrorLayout(errorType = uiState.error)
        }

        AnimatedVisibility(
            viewModel.collectionCompaniesList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Log.d("dat>>", viewModel.collectionCompaniesList.toString())
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //.verticalScroll(rememberScrollState())
                    .padding(top = 16.dp, bottom = 32.dp),
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
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() },
                    )

                    Text(
                        text = collectionName,
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

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    //state = rememberLazyGridState(),
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(viewModel.collectionCompaniesList.size) { index ->
                        val item = viewModel.collectionCompaniesList[index]

                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(169.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
                                .clickable {
                                    navController.navigate("company-details/${item.company.id}")
                                },
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Transparent),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.aia_logo),
                                    contentDescription = "Company Logo",
                                    modifier = Modifier
                                        .size(80.dp),
                                    contentScale = ContentScale.Fit
                                )

                                Text(
                                    textAlign = TextAlign.Center,
                                    text = item.company.name,
                                    maxLines = 1,
                                    softWrap = true,
                                    overflow = TextOverflow.Ellipsis,
                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                    fontWeight = FontWeight.W500,
                                    color = Color(0xFF4A4A4A),
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 14.dp)
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.Transparent),
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "${item.jobOpeningCount} job",
                                        maxLines = 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 12.sp,
                                    )

                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = "openings",
                                        maxLines = 1,
                                        softWrap = true,
                                        overflow = TextOverflow.Ellipsis,
                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        fontWeight = FontWeight.W400,
                                        color = Color(0xFF6A6A6A),
                                        fontSize = 12.sp,
                                    )
                                }
                            }
                        }
                    }
                }

                /*Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.gradient_line),
                contentDescription = "Gradient Line",
                modifier = Modifier
                    .size(4.dp, 18.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Recommended companies",
                modifier = Modifier.padding(start = 4.dp),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 14.sp,
            )
        }

        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            state = rememberLazyGridState(),
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(169.dp)
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(1.dp, Color(0xFFE4E7ED), RoundedCornerShape(8.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.aia_logo),
                            contentDescription = "Company Logo",
                            modifier = Modifier
                                .size(80.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = "Alibaba",
                            maxLines = 1,
                            softWrap = true,
                            overflow = TextOverflow.Ellipsis,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W500,
                            color = Color(0xFF4A4A4A),
                            fontSize = 14.sp,
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                textAlign = TextAlign.Center,
                                text = "3 job",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6A6A6A),
                                fontSize = 12.sp,
                            )

                            Text(
                                textAlign = TextAlign.Center,
                                text = "openings",
                                maxLines = 1,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF6A6A6A),
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }

        }*/
            }
        }
    }
}

@Composable
fun MyToast(message: String) {
    Toast.makeText(LocalContext.current.applicationContext, message, Toast.LENGTH_SHORT).show()
}


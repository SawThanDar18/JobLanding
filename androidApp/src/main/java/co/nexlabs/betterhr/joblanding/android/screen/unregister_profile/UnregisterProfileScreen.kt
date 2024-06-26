
package co.nexlabs.betterhr.joblanding.android.screen.unregister_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UnregisterProfileScreen(navController: NavController) {

    var settingMenuVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp, 16.dp, 16.dp, 0.dp)
                .fillMaxWidth(),
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
                        settingMenuVisible = true
                               },
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.height(100.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.register_profile),
                contentDescription = "Profile Register Warning Image",
                modifier = Modifier.size(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Register Now",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF1ED292),
                fontSize = 18.sp,
                modifier = Modifier.clickable {navController.navigate("register-screen")},
            )
        }

    }

    if (settingMenuVisible) {
        ModalBottomSheetLayout(
            sheetContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 72.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Menu",
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight.W600,
                            color = Color(0xFF6A6A6A),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Image(
                            painter = painterResource(id = R.drawable.x),
                            contentDescription = "Close Icon",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    settingMenuVisible = false
                                },
                            alignment = Alignment.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("about-screen")
                                settingMenuVisible = false
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.alert_circle),
                                contentDescription = "Setting About Icon",
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = "About",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = "Go Icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.language),
                                contentDescription = "Setting Language Icon",
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = "Language",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = "Go Icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("country-screen")
                                settingMenuVisible = false
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.country),
                                contentDescription = "Setting Country Icon",
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = "Country",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = "Go Icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("terms-of-service-screen")
                                settingMenuVisible = false
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.terms),
                                contentDescription = "Setting Terms Icon",
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = "Terms of service",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = "Go Icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("privacy-screen")
                                settingMenuVisible = false
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.privacy),
                                contentDescription = "Setting Privacy Icon",
                                modifier = Modifier
                                    .size(24.dp)
                            )

                            Text(
                                text = "Privacy",
                                modifier = Modifier.padding(start = 16.dp),
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontWeight = FontWeight.W400,
                                color = Color(0xFF757575),
                                fontSize = 14.sp,
                            )
                        }

                        Image(
                            painter = painterResource(id = R.drawable.chevron_right),
                            contentDescription = "Go Icon",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }

                }
            },
            sheetState = rememberModalBottomSheetState(
                initialValue = ModalBottomSheetValue.Expanded
            ),
            sheetShape = RoundedCornerShape(
                bottomStart = 0.dp,
                bottomEnd = 0.dp,
                topStart = 24.dp,
                topEnd = 24.dp
            ),
            sheetElevation = 16.dp,
            sheetBackgroundColor = Color.White,
            sheetContentColor = contentColorFor(Color.White),
            modifier = Modifier.fillMaxWidth(),
            scrimColor = Color.Transparent
        ) {

        }
    }
}

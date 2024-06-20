package co.nexlabs.betterhr.joblanding.android.screen.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.TextButton
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.ConfirmDialog
import co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.inbox.ReasonDialog
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitOfferViewModel
import co.nexlabs.betterhr.joblanding.network.api.setting.SettingViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, viewModel: SettingViewModel) {
    var showDialogForSure by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                text = "Settings",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 14.sp,
            )

            Text(
                text = "hello",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color.Transparent,
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth().height(1.dp)
            .background(color = Color(0xFFE4E7ED)))

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("saved-jobs-screen")
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
                        painter = painterResource(id = R.drawable.settings_save),
                        contentDescription = "Setting Save Icon",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = "Saved jobs",
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                               navController.navigate("about-screen")
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

            Spacer(modifier = Modifier.height(32.dp))

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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("country-screen")
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("privacy-screen")
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("terms-of-service-screen") },
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                               navController.navigate("qr-scan-login-screen")
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
                        painter = painterResource(id = R.drawable.setting_qr),
                        contentDescription = "Setting QR Icon",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = "QR Log in",
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

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            showDialogForSure = true
                        }
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
                        painter = painterResource(id = R.drawable.logout),
                        contentDescription = "Setting Logout Icon",
                        modifier = Modifier
                            .size(24.dp)
                    )

                    Text(
                        text = "Logout",
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

            if (showDialogForSure) {
                ConfirmLogOutDialog(viewModel, navController, onDismiss = { showDialogForSure = false })
            }

        }
    }
}

@Composable
fun ConfirmLogOutDialog(
    viewModel: SettingViewModel, navController: NavController, onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Box(
            modifier = Modifier
                .width(328.dp)
                .height(191.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.confirm_logout),
                    contentDescription = "Confirm Logout Icon",
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Are you sure you want to logout?",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color(0xFF6A6A6A)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = "Cancel",
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFFAAAAAA)
                        )
                    }

                    TextButton(onClick = {
                        viewModel.clearLocalData()
                        navController.navigate("bottom-navigation-screen/${viewModel.getPageId()}/${"profile"}")
                    }) {
                        Text(
                            text = "Logout",
                            fontWeight = FontWeight.W600,
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            color = Color(0xFFF8CB2E)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
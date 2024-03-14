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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.nexlabs.betterhr.joblanding.android.R
import co.nexlabs.betterhr.joblanding.android.theme.colorGreen
import co.nexlabs.betterhr.joblanding.android.theme.colorGrey
import co.nexlabs.betterhr.joblanding.android.theme.dp_0
import co.nexlabs.betterhr.joblanding.android.theme.dp_100
import co.nexlabs.betterhr.joblanding.android.theme.dp_16
import co.nexlabs.betterhr.joblanding.android.theme.dp_20
import co.nexlabs.betterhr.joblanding.android.theme.dp_300
import co.nexlabs.betterhr.joblanding.android.theme.dp_38
import co.nexlabs.betterhr.joblanding.android.theme.dp_8
import co.nexlabs.betterhr.joblanding.android.theme.fontWeight_600
import co.nexlabs.betterhr.joblanding.android.theme.registerScreen
import co.nexlabs.betterhr.joblanding.android.theme.sp_18
import co.nexlabs.betterhr.joblanding.android.theme.sp_24

@Composable
fun UnregisterProfileScreen(navController: NavController) {
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(dp_16, dp_38, dp_16, dp_0)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Profile",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_600,
                color = colorGrey,
                fontSize = sp_24
            )
            Spacer(modifier = Modifier.width(dp_8))

            Image(
                painter = painterResource(id = R.drawable.setting),
                contentDescription = "Setting Icon",
                modifier = Modifier
                    .size(dp_20)
                    .clickable { showMenu = true },
                alignment = Alignment.Center
            )

            /*if (showMenu) {
                BottomSheetMenu(
                    onClose = { showMenu = false }
                )
            }*/
        }


        Spacer(modifier = Modifier.height(dp_100))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.register_profile),
                contentDescription = "Profile Register Warning Image",
                modifier = Modifier.size(dp_300)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Register Now",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = fontWeight_600,
                color = colorGreen,
                fontSize = sp_18,
                modifier = Modifier.clickable { navController.navigate(registerScreen) },
            )
        }

    }
}

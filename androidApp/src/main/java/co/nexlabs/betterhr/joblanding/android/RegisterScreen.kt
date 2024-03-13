package co.nexlabs.betterhr.joblanding.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RegisterScreen(navController: NavController) {

    val context = LocalContext.current
    val otpVal: String? = null

    Column(
        modifier = Modifier.padding(16.dp, 38.dp, 16.dp, 0.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { navController.navigate("unregister_profile_screen") },
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Register",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF6A6A6A),
                fontSize = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Your phone number*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W600,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldWithCornerColor()

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "lost your phone number?",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF1082DE),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Enter OTP Code*",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFF4A4A4A),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OTPTextFields(
            length = 6
        ) { getOpt ->
            otpVal
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)) {
            Image(
                painter = painterResource(id = R.drawable.alert_circle_outline),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                alignment = Alignment.Center
            )
            Spacer(modifier = Modifier.width(4.dp))
            androidx.compose.material3.Text(
                text = "Get instead OTP code",
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontWeight = FontWeight.W400,
                color = Color(0xFFAAAAAA),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(0.dp, 32.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Already have an account?",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W400,
                    color = Color(0xFF6A6A6A),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "log in",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFF1ED292),
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(50.dp).fillMaxWidth()
                    .background(color = Color(0xFFD9D9D9), shape = MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Next",
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontWeight = FontWeight.W600,
                    color = Color(0xFFFFFFFF),
                    fontSize = 14.sp
                )
            }

        }

    }
}
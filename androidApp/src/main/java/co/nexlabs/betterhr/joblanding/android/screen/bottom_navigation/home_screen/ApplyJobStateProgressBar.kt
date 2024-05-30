package co.nexlabs.betterhr.joblanding.android.screen.bottom_navigation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.nexlabs.betterhr.joblanding.android.R

@Composable
fun ApplyJobStateProgressBar(steps: List<ApplyJobStepData>) {
    val completedColor = Color(0xFF1ED292)
    val defaultColor = Color(0xFFE1E1E1)
    val lineWidth = 4.dp

    Row(
        modifier = Modifier
            .fillMaxWidth().height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, step ->
            val circleColor = when {
                step.isCompleted -> completedColor
                step.isInState -> defaultColor
                else -> defaultColor
            }

            Box(contentAlignment = Alignment.Center) {
                if (step.isCompleted) {
                    Image(
                        painter = painterResource(id = R.drawable.marked),
                        contentDescription = "Marked Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )
                } else if (step.isInState) {
                    Image(
                        painter = painterResource(id = R.drawable.in_state),
                        contentDescription = "Marked Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.not_in_state),
                        contentDescription = "Marked Icon",
                        modifier = Modifier
                            .size(24.dp),
                        alignment = Alignment.Center
                    )
                }
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .height(lineWidth)
                        .weight(1f)
                        .background(
                            color = if (steps[index + 1].isInState || step.isCompleted) completedColor else defaultColor,
                            shape = RectangleShape
                        )
                )
            }
        }
    }
}
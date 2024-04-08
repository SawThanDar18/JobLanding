package co.nexlabs.betterhr.joblanding.android.screen.choose_country

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomSpinner(label: String, items: List<String>) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable {
                showDialog = true
            }
            .padding(16.dp)
            .size(200.dp)
            .background(Color.Gray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label)
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = label)
            },
            buttons = {
                Column {
                    items.forEach { item ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = item,
                            modifier = Modifier
                                .clickable {
                                    // Handle item click here
                                    showDialog = false
                                }
                        )
                    }
                }
            }
        )
    }
}
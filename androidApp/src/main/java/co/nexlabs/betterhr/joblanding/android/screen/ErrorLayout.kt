package co.nexlabs.betterhr.joblanding.android.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import co.nexlabs.betterhr.joblanding.util.UIErrorType

@Composable
fun ErrorLayout(
    errorType: UIErrorType,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (errorType) {
            UIErrorType.Network -> MyToast("No internet!")
            is UIErrorType.Other -> MyToast(errorType.error)
            else -> {/* no-op */
            }
        }
    }

}

@Composable
fun MyToast(message: String) {
    Toast.makeText(LocalContext.current.applicationContext, message, Toast.LENGTH_SHORT).show()
}
package co.nexlabs.betterhr.joblanding.android.screen.example

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.nexlabs.betterhr.joblanding.android.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetMenu(
    onClose: () -> Unit
) {
    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = sheetState, sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(Color.White)
            ) {
                Column() {
                    /**
                         * Perform operation in Share
                         */
                    /**
                         * Perform operation in Share
                         */
                    RowItem(title = "Share", resourceId = R.drawable.setting, onClick = {
                        /**
                         * Perform operation in Share
                         */
                    })
                    /**
                         * Perform operation in Upload
                         */
                    /**
                         * Perform operation in Upload
                         */
                    RowItem(title = "Upload", resourceId = R.drawable.alert_circle_outline, onClick = {
                        /**
                         * Perform operation in Upload
                         */
                    })
                    /**
                         * Perform operation in Copy
                         */
                    /**
                         * Perform operation in Copy
                         */
                    RowItem(title = "Copy", resourceId = R.drawable.arrow_left, onClick = {
                        /**
                         * Perform operation in Copy
                         */
                    })
                    /**
                         * Perform operation in Print
                         */
                    /**
                         * Perform operation in Print
                         */
                    RowItem(title = "Print this page", resourceId = R.drawable.setting, onClick = {
                        /**
                         * Perform operation in Print
                         */
                    })
                }
            }
        }, sheetPeekHeight = 48.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(1f)
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    if (sheetState.bottomSheetState.isCollapsed) sheetState.bottomSheetState.expand()
                    else sheetState.bottomSheetState.collapse()
                }
            }) {
                Text(text = "Toggle bottom sheet")
            }
        }
    }
}


@Composable
fun RowItem(title: String, @DrawableRes resourceId: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(40.dp)
            .clickable { onClick },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Image(
            painter = painterResource(id = resourceId), contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}
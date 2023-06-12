package com.jamesthacker.countrysample.ui.common

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    navigationIcon: Painter?,
    navButtonContentDescription: String?,
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onActionClick) {
                    Icon(navigationIcon, navButtonContentDescription)
                }
            }
        }
    )
}

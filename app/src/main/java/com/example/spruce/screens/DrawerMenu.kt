package com.example.spruce.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.CardTravel
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MonochromePhotos
import androidx.compose.material.icons.rounded.Nature
import androidx.compose.material.icons.rounded.PermMedia
import androidx.compose.material.icons.rounded.SevereCold
import androidx.compose.material.icons.rounded.TravelExplore
import androidx.compose.material.icons.rounded.Water
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class DrawerMenu(
    val id: Int,
    val icon: ImageVector,
    val title: String,
    val route: String,
    val modifier: Modifier = Modifier
)


package com.example.spaceexplorer.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import spaceexplorer.composeapp.generated.resources.Res
import spaceexplorer.composeapp.generated.resources.orbitron

@Composable
fun OrbitronFontFamily(): FontFamily = FontFamily(
    Font(Res.font.orbitron, FontWeight.Normal),
)
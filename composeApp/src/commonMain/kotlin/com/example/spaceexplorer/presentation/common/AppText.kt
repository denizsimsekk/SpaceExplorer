package com.example.spaceexplorer.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spaceexplorer.presentation.theme.OrbitronFontFamily

@Composable
fun AppText(
    text: String,
    color: Color = Color.White,
    fontSize: Int = 14,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    underline: Boolean = false
) {
    Text(
        text = text,
        color = color,
        modifier = modifier.padding(top = 4.dp),
        fontSize = fontSize.sp,
        textAlign = textAlign,
        style = TextStyle(
            fontFamily = OrbitronFontFamily(),
            textDecoration = if (underline) TextDecoration.Underline else TextDecoration.None
        )
    )
}
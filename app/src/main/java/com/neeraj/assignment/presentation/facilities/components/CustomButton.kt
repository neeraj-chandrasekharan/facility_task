package com.neeraj.assignment.presentation.facilities.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textColor: Color,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = color,
            disabledContainerColor = Color.Gray
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 15.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        enabled = enabled
    ) {
        Text(
            text = text,
            color = textColor,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview
@Composable
fun ButtonDemo() {
    CustomButton(
        modifier = Modifier,
        text = "Title",
        color = Color.Green,
        textColor = Color.White
    ) {

    }
}
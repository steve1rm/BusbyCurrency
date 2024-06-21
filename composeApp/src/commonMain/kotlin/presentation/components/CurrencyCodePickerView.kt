package presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.CurrencyCode
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.primaryColor
import ui.theme.surfaceColor
import ui.theme.textColor

@Composable
fun CurrencyCodePickerView(
    modifier: Modifier = Modifier,
    currencyCode: CurrencyCode,
    isSelected: Boolean,
    onSelected: (currencyCode: CurrencyCode) -> Unit
) {

    val saturation by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val rememberSaturation = remember(saturation) {
        ColorMatrix().apply {
            this.setToSaturation(saturation)
        }
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable {
                onSelected(currencyCode)
            }
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(currencyCode.flag),
                contentDescription = null,
                colorFilter = ColorFilter.colorMatrix(rememberSaturation)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = Modifier.alpha(animatedAlpha),
                text = currencyCode.country,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }

        CurrencyCodeSelector(isSelected = isSelected)
    }
}

@Composable
fun CurrencyCodeSelector(isSelected: Boolean = false) {
    val animatedColor by animateColorAsState(
        targetValue = if(isSelected) primaryColor else textColor.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(animatedColor),
        contentAlignment = Alignment.Center
    ) {
        if(isSelected) {
            Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "Checkmark icon",
                tint = surfaceColor
            )
        }
    }
}

@Composable
@Preview
fun PreviewCurrencyCodeSelector(isSelected: Boolean) {
    MaterialTheme {
        CurrencyCodeSelector()
    }
}

@Composable
@Preview
fun PreviewCurrencyCodePickerView() {
    MaterialTheme {
        CurrencyCodePickerView(
            currencyCode = CurrencyCode.THB,
            isSelected = true,
            onSelected = {}
        )
    }
}
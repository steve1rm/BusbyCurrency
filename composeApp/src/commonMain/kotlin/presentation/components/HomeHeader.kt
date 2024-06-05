package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import busbycurrency.composeapp.generated.resources.Res
import busbycurrency.composeapp.generated.resources.exchange_illustration
import busbycurrency.composeapp.generated.resources.refresh_ic
import busbycurrency.composeapp.generated.resources.start_refresh
import domain.model.RateStatus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.utils.displayCurrentDateTime
import ui.theme.headerColor
import ui.theme.staleColor

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    ratesStatus: RateStatus,
    onRateRefreshClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
            .background(color = headerColor)
            .padding(all = 24.dp),
    ) {
        RatesStatus(
            ratesStatus = ratesStatus,
            onRateRefreshClicked = onRateRefreshClicked)

       /* Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null
            )

            Column {
                Text(text = "26th October, 2024", fontSize = 24.sp)
                Text(text = "Rates are not fresh", fontSize = 16.sp)
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    modifier = Modifier.fillMaxWidth(),
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )

            }
        }*/
    }
}

@Composable
fun RatesStatus(
    ratesStatus: RateStatus,
    onRateRefreshClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(50.dp),
                painter = painterResource(Res.drawable.exchange_illustration),
                contentDescription = "Exchange rate illustration"
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = displayCurrentDateTime(),
                    color = Color.White
                )
                Text(
                    text = stringResource(ratesStatus.title),
                    color = ratesStatus.color,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
        }

        if(ratesStatus == RateStatus.Stale) {
            IconButton(
                onClick = onRateRefreshClicked
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.refresh_ic),
                    contentDescription = stringResource(Res.string.start_refresh),
                    tint = staleColor
                )
            }
        }
    }
}


@Composable
@Preview
fun PreviewHomeHeader() {
    MaterialTheme {
        HomeHeader(
            ratesStatus = RateStatus.Fresh,
            onRateRefreshClicked = {}
        )
    }
}
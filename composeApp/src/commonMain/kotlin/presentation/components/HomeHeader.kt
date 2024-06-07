package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import busbycurrency.composeapp.generated.resources.Res
import busbycurrency.composeapp.generated.resources.compose_multiplatform
import busbycurrency.composeapp.generated.resources.exchange_illustration
import busbycurrency.composeapp.generated.resources.refresh_ic
import busbycurrency.composeapp.generated.resources.start_refresh
import busbycurrency.composeapp.generated.resources.switch_ic
import domain.RequestState
import domain.model.CurrencyCode
import domain.model.CurrencyModel
import domain.model.RateStatus
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.utils.displayCurrentDateTime
import ui.theme.headerColor
import ui.theme.staleColor

@Composable
fun HomeHeader(
    ratesStatus: RateStatus,
    source: RequestState<CurrencyModel>,
    target: RequestState<CurrencyModel>,
    modifier: Modifier = Modifier,
    onSwitchClicked: () -> Unit,
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

        Spacer(modifier = Modifier.height(24.dp))

        CurrencyInput(
            source = source,
            target = target,
            onSwitchClicked = onSwitchClicked
        )
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
fun CurrencyInput(
    source: RequestState<CurrencyModel>,
    target: RequestState<CurrencyModel>,
    onSwitchClicked: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CurrencyView(
            placeholder = "From",
            requestState = source,
            onCurrencyClicked = {}
        )

        Spacer(modifier = Modifier.width(14.dp))

        IconButton(
            onClick = onSwitchClicked
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch currencies",
                tint = Color.White)
        }

        CurrencyView(
            placeholder = "To",
            requestState = target,
            onCurrencyClicked = {}
        )
    }
}

@Composable
fun RowScope.CurrencyView(
    placeholder: String,
    requestState: RequestState<CurrencyModel>,
    onCurrencyClicked: () -> Unit
) {

    Column(
        modifier = Modifier.weight(1f)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = placeholder,
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            color = Color.White)

        Spacer(modifier = Modifier.height(4.dp))

        if(requestState.isSuccess()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(size = 8.dp))
                    .background(Color.White.copy(alpha = 0.6f))
                    .height(54.dp)
                    .clickable(
                        onClick = onCurrencyClicked
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Click to select currency",
                    painter = painterResource(getCurrencyResFromCode(requestState.getSuccessData()).first),
                    tint = Color.Unspecified
                )

                Text(
                    text = getCurrencyResFromCode(requestState.getSuccessData()).second,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = Color.White)
            }

        }
    }

}

private fun getCurrencyResFromCode(currencyModel: CurrencyModel?): Pair<DrawableResource, String> {
    return if(currencyModel != null) {
        CurrencyCode.valueOf(currencyModel.code).flag to CurrencyCode.valueOf(currencyModel.code).country
    }
    else {
        Res.drawable.compose_multiplatform to "UNK"
    }
}

@Composable
@Preview
fun PreviewHomeHeader() {
    MaterialTheme {
        HomeHeader(
            ratesStatus = RateStatus.Fresh,
            onRateRefreshClicked = {},
            onSwitchClicked = { },
            source = RequestState.Success(CurrencyModel("", 0.0)),
            target = RequestState.Success(CurrencyModel("", 0.0))
        )
    }
}
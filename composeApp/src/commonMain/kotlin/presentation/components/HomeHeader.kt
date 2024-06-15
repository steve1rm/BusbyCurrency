package presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
    amount: Double,
    modifier: Modifier = Modifier,
    onSwitchClicked: () -> Unit,
    onRateRefreshClicked: () -> Unit,
    onAmountChange: (amount: Double) -> Unit
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

        Spacer(modifier = Modifier.height(24.dp))

        AmountInput(
            amount = amount,
            onAmountChange = onAmountChange
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
fun AmountInput(
    amount: Double,
    onAmountChange: (amount: Double) -> Unit) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .animateContentSize()
            .height(54.dp),
        value = "$amount",
        onValueChange = {
            onAmountChange(it.toDouble())
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White.copy(alpha = 0.05f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f),
            errorContainerColor = Color.White.copy(alpha = 0.05f),
            focusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White
        ),
        textStyle = LocalTextStyle.current.copy(
            color = Color.White,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        )
    )
}

@Composable
fun CurrencyInput(
    source: RequestState<CurrencyModel>,
    target: RequestState<CurrencyModel>,
    onSwitchClicked: () -> Unit
) {
    var animationStarted by remember {
        mutableStateOf(false)
    }
    val animatedRotation by animateFloatAsState(
        targetValue = if(animationStarted) 180f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
    ) {
        CurrencyView(
            placeholder = "From",
            requestState = source,
            onCurrencyClicked = {}
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            modifier = Modifier
                .graphicsLayer {
                    this.rotationY = animatedRotation
                },
            onClick = {
                animationStarted = !animationStarted
                onSwitchClicked()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.switch_ic),
                contentDescription = "Switch currencies",
                tint = Color.White)
        }

        Spacer(modifier = Modifier.width(8.dp))

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
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = Color.White)

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(size = 8.dp))
                .background(Color.White.copy(alpha = 0.05f))
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

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = getCurrencyResFromCode(requestState.getSuccessData()).second,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = Color.White)
        }
    }
}

private fun getCurrencyResFromCode(currencyModel: CurrencyModel?): Pair<DrawableResource, String> {
    return if(currencyModel != null) {
        CurrencyCode.valueOf(currencyModel.code).flag to CurrencyCode.valueOf(currencyModel.code).name
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
            source = RequestState.Success(CurrencyModel()),
            target = RequestState.Success(CurrencyModel()),
            amount = 0.0,
            onAmountChange = {}
        )
    }
}
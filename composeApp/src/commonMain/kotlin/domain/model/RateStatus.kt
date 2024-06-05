package domain.model

import androidx.compose.ui.graphics.Color
import busbycurrency.composeapp.generated.resources.Res
import busbycurrency.composeapp.generated.resources.fresh_rates
import busbycurrency.composeapp.generated.resources.rates
import busbycurrency.composeapp.generated.resources.stale
import org.jetbrains.compose.resources.StringResource
import ui.theme.freshColor
import ui.theme.staleColor

enum class RateStatus(
    val title: StringResource = Res.string.rates,
    val color: Color = Color.White
) {

    Idle(
        title = Res.string.rates,
        color = Color.White
    ),
    Fresh(
        title = Res.string.fresh_rates,
        color = freshColor
    ),
    Stale(
        title = Res.string.stale,
        color = staleColor
    );
}

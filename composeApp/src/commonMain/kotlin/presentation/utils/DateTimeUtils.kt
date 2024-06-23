package presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import busbycurrency.composeapp.generated.resources.Res
import busbycurrency.composeapp.generated.resources.bebas_neue_regular
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.Font

fun displayCurrentDateTime(): String {
    val currentTimestamp = Clock.System.now()
    val date = currentTimestamp.toLocalDateTime(TimeZone.currentSystemDefault())

    val dayOfMonth = date.dayOfMonth
    val month = date.month.toString().lowercase()
        .replaceFirstChar { letter ->
            if(letter.isLowerCase()) {
                letter.titlecase()
            }
            else {
                letter.toString()
            }
        }
    val year = date.year

    /** Determine the suffix for the day of the month */
    val suffix = when {
        dayOfMonth in 11..13 -> "th"
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    return "$dayOfMonth$suffix $month, $year"
}

@Composable
fun GetBebasFontFamily(): FontFamily {
    return FontFamily(Font(Res.font.bebas_neue_regular))
}

private fun displayCurrentDateTimeExt(): String {
    val localTimeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.todayIn(localTimeZone)

    return "${today.dayOfMonth}th ${today.month}, ${today.year}"
}
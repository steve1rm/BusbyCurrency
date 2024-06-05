package presentation.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

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


private fun displayCurrentDateTimeExt(): String {
    val localTimeZone = TimeZone.currentSystemDefault()
    val today = Clock.System.todayIn(localTimeZone)

    return "${today.dayOfMonth}th ${today.month}, ${today.year}"
}
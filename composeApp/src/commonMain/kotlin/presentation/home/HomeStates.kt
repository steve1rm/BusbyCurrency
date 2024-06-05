package presentation.home

import domain.model.RateStatus

data class HomeStates(
    val rateStatus: RateStatus = RateStatus.Idle
)

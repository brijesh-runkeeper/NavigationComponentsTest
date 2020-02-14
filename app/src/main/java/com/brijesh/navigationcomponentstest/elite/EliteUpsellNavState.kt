package com.brijesh.navigationcomponentstest.elite

import com.brijesh.navigationcomponentstest.OnboardingNavComplete
import com.brijesh.navigationcomponentstest.OnboardingNavEvent
import com.brijesh.navigationcomponentstest.OnboardingNavState
import io.reactivex.Single

sealed class EliteUpsellViewEvent
object EliteUpsellNavBack: EliteUpsellViewEvent()
object EliteUpsellNavForward: EliteUpsellViewEvent()

class EliteUpsellNavState(viewEvent: Single<EliteUpsellViewEvent>): OnboardingNavState {
    override val navEvents: Single<OnboardingNavEvent> = viewEvent.map { OnboardingNavComplete }
}
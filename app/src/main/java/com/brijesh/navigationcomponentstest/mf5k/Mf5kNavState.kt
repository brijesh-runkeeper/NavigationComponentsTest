package com.brijesh.navigationcomponentstest.mf5k

import com.brijesh.navigationcomponentstest.*
import io.reactivex.Single

sealed class Mf5kViewEvent
data class Mf5kNext(val comped: Boolean) : Mf5kViewEvent()
object Mf5kBack : Mf5kViewEvent()

class Mf5kNavState(viewEvent: Single<Mf5kViewEvent>) : OnboardingNavState {
    override val navEvents: Single<OnboardingNavEvent> = viewEvent
        .map { event ->
            when (event) {
                is Mf5kBack -> return@map OnboardingNavBack(false)
                is Mf5kNext -> return@map processNextEvent(event)
            }
        }

    private fun processNextEvent(event: Mf5kNext): OnboardingNavEvent {
        return if (event.comped) {
            OnboardingNavComplete
        } else {
            OnboardingNavForward(Mf5kIntroDirections.actionMf5kIntroToEliteUpsell())
        }
    }
}
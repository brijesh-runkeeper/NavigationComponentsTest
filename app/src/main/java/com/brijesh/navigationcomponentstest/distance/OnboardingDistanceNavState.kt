package com.brijesh.navigationcomponentstest.distance

import com.brijesh.navigationcomponentstest.*
import io.reactivex.Single

sealed class OnboardingDistanceViewEvent
data class OnboardingDistanceViewEventNext(
    val moreThanThree: Boolean,
    val comped: Boolean
) : OnboardingDistanceViewEvent()

object OnboardingDistanceViewEventBack : OnboardingDistanceViewEvent()

class OnboardingDistanceNavState(viewEvents: Single<OnboardingDistanceViewEvent>) :
    OnboardingNavState {

    override val navEvents: Single<OnboardingNavEvent> = viewEvents
        .map { event ->
            when (event) {
                is OnboardingDistanceViewEventBack -> return@map OnboardingNavBack(false)
                is OnboardingDistanceViewEventNext -> {
                    return@map processForwardEvent(event)
                }
            }
        }

    private fun processForwardEvent(event: OnboardingDistanceViewEventNext): OnboardingNavEvent {
        if (event.moreThanThree) {
            if (event.comped) {
                return OnboardingNavComplete
            }
            return OnboardingNavForward(OnboardingDistanceQuestionDirections.actionOnboardingDistanceQuestionToEliteUpsell())
        } else {
            return OnboardingNavForward(OnboardingDistanceQuestionDirections.actionOnboardingDistanceQuestionToMf5kIntro())
        }
    }
}
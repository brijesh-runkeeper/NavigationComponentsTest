package com.brijesh.navigationcomponentstest.signupreason

import com.brijesh.navigationcomponentstest.OnboardingNavEvent
import com.brijesh.navigationcomponentstest.OnboardingNavForward
import com.brijesh.navigationcomponentstest.OnboardingNavState
import com.brijesh.navigationcomponentstest.OnboardingNavBack
import io.reactivex.Single

sealed class OnboardingSignupReasonViewEvent
object OnboardingSignupReasonForward: OnboardingSignupReasonViewEvent()
object OnboardingSignupReasonBack: OnboardingSignupReasonViewEvent()

class OnboardingSignupReasonNavState(viewEvents: Single<OnboardingSignupReasonViewEvent>): OnboardingNavState {
    override val navEvents: Single<OnboardingNavEvent> = viewEvents
        .map { event ->
            when(event) {
                is OnboardingSignupReasonForward -> return@map OnboardingNavForward(
                    OnboardingSignupReasonDirections.actionOnboardingSignupReasonToOnboardingDistanceQuestion())
                is OnboardingSignupReasonBack -> return@map OnboardingNavBack(false)
            }
        }
}
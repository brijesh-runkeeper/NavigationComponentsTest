package com.brijesh.navigationcomponentstest

import io.reactivex.Single

/**
 * Interface that represents each step of onboarding
 */
interface OnboardingNavState {
    /**
     * Each step of onboarding needs to emit nav events for when the user decides to move forward or
     * back. Depending on the step, a nav event might imply exiting onboarding.
     * See OnboardingNavEvent
     */
    val navEvents: Single<OnboardingNavEvent>
}
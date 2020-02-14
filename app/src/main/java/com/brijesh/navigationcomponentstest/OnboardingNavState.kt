package com.brijesh.navigationcomponentstest

import io.reactivex.Single

interface OnboardingNavState {
    val navEvents: Single<OnboardingNavEvent>
}
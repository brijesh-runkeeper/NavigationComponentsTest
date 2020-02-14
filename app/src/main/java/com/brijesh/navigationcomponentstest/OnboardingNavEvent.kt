package com.brijesh.navigationcomponentstest

import androidx.navigation.NavDirections

sealed class OnboardingNavEvent

data class OnboardingNavBack(val root: Boolean): OnboardingNavEvent()
data class OnboardingNavForward(val navTransition: NavDirections): OnboardingNavEvent()
object OnboardingNavComplete: OnboardingNavEvent()
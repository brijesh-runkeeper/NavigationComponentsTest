package com.brijesh.navigationcomponentstest

import androidx.navigation.NavDirections

/**
 * This is the kind of event that each instance of OnboardingNavState emits. This indicates that
 * a navigation is required
 */
sealed class OnboardingNavEvent

/**
 * This is a navigation request to go back with the back button
 * @param root: This is true if, once we go back, we need to exit navigation
 */
data class OnboardingNavBack(val root: Boolean): OnboardingNavEvent()

/**
 * Navigate forward
 * @param navTransition: Instance NavDirections. This is required by NavigationHelper to know where
 * to navigate to.
 */
data class OnboardingNavForward(val navTransition: NavDirections): OnboardingNavEvent()

/**
 * This marks that the current onboarding is complete
 */
object OnboardingNavComplete: OnboardingNavEvent()
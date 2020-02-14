package com.brijesh.navigationcomponentstest

import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Interface that is responsible for navigating to a destination given an instance of NavDirections
 */
interface NavigationHelper {
    fun navigateTo(navDirections: NavDirections)
}

/**
 * Wrapper around androidx's NavController
 */
class NavControllerWrapper(private val underlyingController: NavController): NavigationHelper {
    override fun navigateTo(navDirections: NavDirections) {
        underlyingController.navigate(navDirections)
    }

}
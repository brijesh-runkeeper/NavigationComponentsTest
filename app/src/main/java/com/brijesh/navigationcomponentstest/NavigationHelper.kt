package com.brijesh.navigationcomponentstest

import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface NavigationHelper {
    fun navigateTo(navDirections: NavDirections)
}

class NavControllerWrapper(private val underlyingController: NavController): NavigationHelper {
    override fun navigateTo(navDirections: NavDirections) {
        underlyingController.navigate(navDirections)
    }

}
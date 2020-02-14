package com.brijesh.navigationcomponentstest

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Event to let the host activity know if onboarding is complete
 */
sealed class OnboardingExitEvent

/**
 * Onboarding was completed
 */
object OnboardingCompleted: OnboardingExitEvent()

/**
 * Onboarding was abandoned
 */
object OnboardingAbandoned: OnboardingExitEvent()

/**
 * This is the ViewModel class that is shared by all of the onboarding states. This is accessible
 * by the host onboarding activity and by the individual onboarding fragments.
 */
class OnboardingViewModel : ViewModel() {
    /**
     * These two vars are state that's set that are used by the navigation steps to determine where
     * to navigate next
     */
    var comped = false
    var isEnglish = true

    var existingNavEventDisposable: Disposable? = null
    private lateinit var navigationHelper: NavigationHelper

    private var onboardingExitEmitter: SingleEmitter<OnboardingExitEvent>? = null
    val onboardingExitEvent: Single<OnboardingExitEvent> = Single.create { onboardingExitEmitter = it }

    fun beginOnboarding(navigationHelper: NavigationHelper) {
        this.navigationHelper = navigationHelper
    }

    /**
     * This event needs to be called by each onboarding step. This will listen to navState's events
     * and navigate based on the event
     */
    fun markCurrentOnboardingState(navState: OnboardingNavState) {
        if (existingNavEventDisposable?.isDisposed == false) {
            existingNavEventDisposable?.dispose()
        }
        existingNavEventDisposable = navState.navEvents
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when(event) {
                    is OnboardingNavForward -> navigationHelper.navigateTo(event.navTransition)
                    is OnboardingNavComplete -> onboardingExitEmitter?.onSuccess(OnboardingCompleted)
                    is OnboardingNavBack -> {
                        if (event.root) {
                            onboardingExitEmitter?.onSuccess(OnboardingAbandoned)
                        }
                    }
                }
            }
    }
}

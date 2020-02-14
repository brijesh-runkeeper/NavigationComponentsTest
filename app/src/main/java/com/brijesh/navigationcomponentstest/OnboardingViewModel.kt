package com.brijesh.navigationcomponentstest

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

sealed class OnboardingExitEvent
object OnboardingCompleted: OnboardingExitEvent()
object OnboardingAbandoned: OnboardingExitEvent()

class OnboardingViewModel : ViewModel() {
    var comped = false
    var isEnglish = true
    var existingNavEventDisposable: Disposable? = null
    private lateinit var navigationHelper: NavigationHelper

    private var onboardingExitEmitter: SingleEmitter<OnboardingExitEvent>? = null
    val onboardingExitEvent: Single<OnboardingExitEvent> = Single.create { onboardingExitEmitter = it }

    fun beginOnboarding(navigationHelper: NavigationHelper) {
        this.navigationHelper = navigationHelper
    }

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

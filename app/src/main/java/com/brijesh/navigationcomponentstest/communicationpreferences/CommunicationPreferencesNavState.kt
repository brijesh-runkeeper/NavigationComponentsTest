package com.brijesh.navigationcomponentstest.communicationpreferences

import com.brijesh.navigationcomponentstest.*
import io.reactivex.Single

sealed class CommunicationPreferencesViewEvent
data class CommunicationPreferencesSaved(val comped: Boolean, val english: Boolean) : CommunicationPreferencesViewEvent()
object CommunicationPreferencesBackPressed : CommunicationPreferencesViewEvent()

class CommunicationPreferencesNavState(
    viewEvents: Single<CommunicationPreferencesViewEvent>
) : OnboardingNavState {
    override val navEvents: Single<OnboardingNavEvent> = viewEvents
        .map { event ->
            when (event) {
                is CommunicationPreferencesBackPressed -> return@map OnboardingNavBack(true)
                is CommunicationPreferencesSaved -> {
                    return@map processCommunicationPreferencesSavedEvent(
                        event.comped,
                        event.english
                    )
                }
            }
        }

    private fun processCommunicationPreferencesSavedEvent(
        isComped: Boolean,
        isEnglish: Boolean
    ): OnboardingNavEvent {
        if (isEnglish) {
            return OnboardingNavForward(
                CommunicationPreferencesFragmentDirections.actionCommunicationPreferencesFragmentToOnboardingSignupReason()
            )
        }
        if (!isComped) {
            return OnboardingNavForward(
                CommunicationPreferencesFragmentDirections.actionCommunicationPreferencesFragmentToEliteUpsell()
            )
        }

        return OnboardingNavComplete
    }
}
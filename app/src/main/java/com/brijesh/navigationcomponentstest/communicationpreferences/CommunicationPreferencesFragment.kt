package com.brijesh.navigationcomponentstest.communicationpreferences

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.brijesh.navigationcomponentstest.OnboardingViewModel
import com.brijesh.navigationcomponentstest.R
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * A simple [Fragment] subclass.
 */
class CommunicationPreferencesFragment : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
    private var navEmitter: SingleEmitter<CommunicationPreferencesViewEvent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback {
            navEmitter?.onSuccess(CommunicationPreferencesBackPressed)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_communication_preferences, container, false)
        view.findViewById<View>(R.id.english_button).setOnClickListener {
            onboardingViewModel.isEnglish = !onboardingViewModel.isEnglish
        }

        view.findViewById<View>(R.id.comp_button).setOnClickListener {
            onboardingViewModel.comped = !onboardingViewModel.comped
        }

        view.findViewById<View>(R.id.preference_saved_button).setOnClickListener {
            navEmitter?.onSuccess(
                CommunicationPreferencesSaved(
                    onboardingViewModel.comped,
                    onboardingViewModel.isEnglish
                )
            )
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val navState =
            CommunicationPreferencesNavState(
                Single.create { navEmitter = it })
        onboardingViewModel.markCurrentOnboardingState(
            navState
        )
    }
}

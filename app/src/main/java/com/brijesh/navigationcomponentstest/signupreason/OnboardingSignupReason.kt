package com.brijesh.navigationcomponentstest.signupreason

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

class OnboardingSignupReason : Fragment() {

    private val viewModel: OnboardingViewModel by activityViewModels()
    private var navEmitter: SingleEmitter<OnboardingSignupReasonViewEvent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback {
            navEmitter?.onSuccess(OnboardingSignupReasonBack)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding_signup_reason, container, false)
        view.findViewById<View>(R.id.next_button).setOnClickListener {
            navEmitter?.onSuccess(OnboardingSignupReasonForward)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val navState = OnboardingSignupReasonNavState(Single.create { navEmitter = it })
        viewModel.markCurrentOnboardingState(navState)
    }
}

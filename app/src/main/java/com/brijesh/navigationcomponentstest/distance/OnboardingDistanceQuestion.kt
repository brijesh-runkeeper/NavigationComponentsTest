package com.brijesh.navigationcomponentstest.distance

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
class OnboardingDistanceQuestion : Fragment() {

    private val viewModel: OnboardingViewModel by activityViewModels<OnboardingViewModel>()
    private var navEmitter: SingleEmitter<OnboardingDistanceViewEvent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback {
            navEmitter?.onSuccess(OnboardingDistanceViewEventBack)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding_distance_question, container, false)

        view.findViewById<View>(R.id.more_than_3).setOnClickListener {
            navEmitter?.onSuccess(OnboardingDistanceViewEventNext(true, viewModel.comped))
        }

        view.findViewById<View>(R.id.less_than_3).setOnClickListener {
            navEmitter?.onSuccess(OnboardingDistanceViewEventNext(false, viewModel.comped))
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val navState = OnboardingDistanceNavState(Single.create { navEmitter = it })
        viewModel.markCurrentOnboardingState(navState)
    }
}

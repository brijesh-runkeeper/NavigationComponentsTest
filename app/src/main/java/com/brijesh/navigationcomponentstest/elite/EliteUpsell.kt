package com.brijesh.navigationcomponentstest.elite

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
class EliteUpsell : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
    private var navEmitter: SingleEmitter<EliteUpsellViewEvent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback { navEmitter?.onSuccess(EliteUpsellNavBack) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_elite_upsell, container, false)
        view.findViewById<View>(R.id.next_button).setOnClickListener { navEmitter?.onSuccess(EliteUpsellNavForward) }
        return view
    }

    override fun onResume() {
        super.onResume()
        val navState = EliteUpsellNavState(Single.create { navEmitter = it })
        onboardingViewModel.markCurrentOnboardingState(navState)
    }
}

package com.brijesh.navigationcomponentstest.mf5k

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
class Mf5kIntro : Fragment() {

    private val onboardingViewModel: OnboardingViewModel by activityViewModels()
    private var navEmitter: SingleEmitter<Mf5kViewEvent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback {
            navEmitter?.onSuccess(Mf5kBack)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mf5k_intro, container, false)
        view.findViewById<View>(R.id.next_button).setOnClickListener {
            navEmitter?.onSuccess(Mf5kNext(onboardingViewModel.comped))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val navState = Mf5kNavState(Single.create { navEmitter = it })
        onboardingViewModel.markCurrentOnboardingState(navState)
    }
}

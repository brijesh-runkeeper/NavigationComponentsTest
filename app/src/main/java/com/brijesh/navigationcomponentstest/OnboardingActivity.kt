package com.brijesh.navigationcomponentstest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers

class OnboardingActivity : AppCompatActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        onboardingViewModel.beginOnboarding(NavControllerWrapper(findNavController(R.id.nav_host_fragment)))
        onboardingViewModel.onboardingExitEvent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when (event) {
                    is OnboardingCompleted -> finish()
                    is OnboardingAbandoned -> {
                        moveTaskToBack(true)
                        finish()
                    }
                }
            }
    }

}

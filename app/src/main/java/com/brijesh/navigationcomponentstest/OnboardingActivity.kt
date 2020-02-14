package com.brijesh.navigationcomponentstest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * The main onboarding activity that hosts the nav graph for all the various nav states.
 * The nav graph automatically places the first destination fragment defined in the graph.
 */
class OnboardingActivity : AppCompatActivity() {

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Give the view model nav controller to navigate to different onboarding destinations
        onboardingViewModel.beginOnboarding(NavControllerWrapper(findNavController(R.id.nav_host_fragment)))

        // Listen for exit events for when onboarding is complete.
        // The completed event could take in extra arguments if we wanted to (say) pass in data
        // to MainActivity
        onboardingViewModel.onboardingExitEvent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when (event) {
                    is OnboardingCompleted -> finish()


                    is OnboardingAbandoned -> {
                        // This is to bypass MainActivity that is likely on the back stack and dismiss
                        // the entire app
                        moveTaskToBack(true)
                        finish()
                    }
                }
            }
    }

}

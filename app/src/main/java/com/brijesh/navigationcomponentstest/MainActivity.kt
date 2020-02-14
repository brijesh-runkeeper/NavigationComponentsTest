package com.brijesh.navigationcomponentstest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * The main activity for the app. This kicks off the onboarding activity. When onboarding completes,
 * this activity is resumed.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.start_onboarding).setOnClickListener {
            startActivity(Intent(this, OnboardingActivity::class.java))
        }
    }
}

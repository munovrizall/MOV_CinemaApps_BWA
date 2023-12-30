package com.artonov.mov.activity_onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.artonov.mov.databinding.ActivityOnboardingFirstBinding
import com.artonov.mov.activity_login.SignInActivity
import com.artonov.mov.utils.Preferences

class OnboardingFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingFirstBinding

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")) {
            finishAffinity()
            startActivity(Intent(this@OnboardingFirstActivity, SignInActivity::class.java))
        }

        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener() {
            val intent = Intent(this@OnboardingFirstActivity, OnboardingSecondActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }

        binding.btnSkip.setOnClickListener() {
            preferences.setValues("onboarding", "1")
            finishAffinity()

            val intent = Intent(this@OnboardingFirstActivity, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }
    }
}
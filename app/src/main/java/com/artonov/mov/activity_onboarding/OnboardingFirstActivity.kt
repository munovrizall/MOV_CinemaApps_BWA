package com.artonov.mov.activity_onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.artonov.mov.databinding.ActivityOnboardingFirstBinding
import com.artonov.mov.activity_login.SignInActivity

class OnboardingFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingFirstBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingFirstBinding.inflate(layoutInflater)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(binding.root)

        binding.btnNext.setOnClickListener() {
            val intent = Intent(this@OnboardingFirstActivity, OnboardingSecondActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }

        binding.btnSkip.setOnClickListener() {
            val intent = Intent(this@OnboardingFirstActivity, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }
    }
}
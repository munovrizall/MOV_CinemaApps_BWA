package com.artonov.mov.activity_onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.artonov.mov.databinding.ActivityOnboardingThirdBinding
import com.artonov.mov.activity_login.SignInActivity

class OnboardingThirdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingThirdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener() {
            val intent = Intent(this@OnboardingThirdActivity, SignInActivity::class.java)
            startActivity(intent)
            overridePendingTransition(androidx.appcompat.R.anim.abc_fade_in, androidx.appcompat.R.anim.abc_fade_out)
        }

    }
}
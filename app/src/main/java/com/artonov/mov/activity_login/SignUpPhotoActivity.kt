package com.artonov.mov.activity_login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.artonov.mov.R
import com.artonov.mov.databinding.ActivitySignUpBinding
import com.artonov.mov.databinding.ActivitySignUpPhotoBinding

class SignUpPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener() {
            finish()
        }
    }
}
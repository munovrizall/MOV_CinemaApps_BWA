package com.artonov.mov.activity_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.artonov.mov.activity_home.HomeActivity
import com.artonov.mov.databinding.ActivitySignInBinding
import com.artonov.mov.utils.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    lateinit var username: String
    lateinit var password: String
    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preferences = Preferences(this)

        preferences.setValues("onboarding", "1") // 1(sudah onboarding), 0(belum onboarding)

        // Jika user sudah login, langsung ke home activity
        if (preferences.getValues("status").equals("1")) {
            finishAffinity()
            startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener() {
            username = binding.etUsername.text.toString()
            password = binding.etPassword.text.toString()

            if (username.isBlank()) {
                binding.etUsername.error = "Username tidak boleh kosong!"
                binding.etUsername.requestFocus()
            } else if (password.isBlank()) {
                binding.etPassword.error = "Password tidak boleh kosong!"
                binding.etPassword.requestFocus()
            } else {
                pushLogin(username, password)
            }
        }

        binding.btnSignUp.setOnClickListener() {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(username: String, password: String) {
        mDatabase.child(username).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)

                if (user == null) {
                    Toast.makeText(
                        this@SignInActivity,
                        "Username tidak ditemukan",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    if (user.password.equals(password)) {
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("nama", user.nama.toString())
                        preferences.setValues("saldo", user.saldo.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("status", "1") // Status 1(sudah login), 0(belum login)

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Password salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@SignInActivity,
                    error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
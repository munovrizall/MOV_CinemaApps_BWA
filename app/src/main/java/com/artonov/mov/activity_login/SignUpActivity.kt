package com.artonov.mov.activity_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.artonov.mov.R
import com.artonov.mov.databinding.ActivitySignUpBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    lateinit var username: String
    lateinit var password: String
    lateinit var nama: String
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        binding.btnBack.setOnClickListener() {
            finish()
        }

        binding.btnNext.setOnClickListener() {
            binding.apply {
                username = etUsername.text.toString()
                password = etPassword.text.toString()
                nama = etNama.text.toString()
                email = etEmail.text.toString()

                if (username.isBlank()) {
                    etUsername.error = "Isi username anda"
                    etUsername.requestFocus()
                } else if (password.isBlank()) {
                    etPassword.error = "Isi password anda"
                    etPassword.requestFocus()
                } else if (nama.isBlank()) {
                    etNama.error = "Isi nama anda"
                    etNama.requestFocus()
                } else if (email.isBlank()) {
                    etEmail.error = "Isi email anda"
                    etEmail.requestFocus()
                } else {
                    saveUser(username, password, nama, email)
                }
            }
        }
    }

    private fun saveUser(username: String, password: String, nama: String, email: String) {
        var user = User()
        user.username = username
        user.password = password
        user.nama = nama
        user.email = email

        if (username != null) {
            checkUserAccount(username, user)
        }
    }

    private fun checkUserAccount(username: String, data: User) {
        mDatabaseReference.child(username).addListenerForSingleValueEvent(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)

                // Jika null akun bisa dibuat
                if (user == null) {
                    mDatabaseReference.child(username).setValue(data)

                    val intent = Intent(this@SignUpActivity, SignUpPhotoActivity::class.java).putExtra("nama", data.nama)
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}
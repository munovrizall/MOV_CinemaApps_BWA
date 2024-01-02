package com.artonov.mov.activity_login

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.artonov.mov.activity_home.HomeActivity
import com.artonov.mov.R
import com.artonov.mov.databinding.ActivitySignUpPhotoBinding
import com.artonov.mov.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {

    private lateinit var binding: ActivitySignUpPhotoBinding
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        binding.tvUsername.text = "Selamat Datang\n ${intent.getStringExtra("nama")}"

        binding.btnAdd.setOnClickListener() {
            if (statusAdd) {
                statusAdd = false
                binding.btnSave.visibility = View.VISIBLE
                binding.btnAdd.setImageResource(R.drawable.ic_delete)
                binding.ivProfile.setImageResource(R.drawable.user_account_photo)
            } else {
                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()
            }
        }

        binding.btnHome.setOnClickListener() {
            finishAffinity()
            startActivity(Intent(this@SignUpPhotoActivity, HomeActivity::class.java))
        }

        binding.btnSave.setOnClickListener() {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener {
                            preferences.setValues("url", it.toString())
                        }

                        finishAffinity()
                        startActivity(Intent(this@SignUpPhotoActivity, HomeActivity::class.java))
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        var progress =
                            100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload " + progress.toInt() + "%")

                    }
            } else {

            }
        }

        binding.btnBack.setOnClickListener() {
            finish()
        }
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Tidak bisa menambahkan photo", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        response: PermissionRequest?,
        token: PermissionToken?
    ) {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Bingung? klik upload nanti aja", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.data!!
            Glide.with(this)
                .load(bitmap)
                .apply { RequestOptions.circleCropTransform() }
                .into(binding.ivProfile)

            binding.btnSave.visibility = View.VISIBLE
            binding.btnAdd.setImageResource(R.drawable.ic_delete)
        }
    }
}
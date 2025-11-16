package com.example.backstagepass

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ShapeableImageView
    private var selectedImageUri: Uri? = null

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri
                profileImageView.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        profileImageView = findViewById(R.id.profileImage)
        val usernameEditText: EditText = findViewById(R.id.editProfileUsername)
        val emailEditText: EditText = findViewById(R.id.editProfileEmail)
        val phoneEditText: EditText = findViewById(R.id.editProfilePhone)
        val roleSpinner: Spinner = findViewById(R.id.spinnerRole)
        val saveButton: Button = findViewById(R.id.buttonSaveProfile)

        val roles = listOf("Booking Manager", "Talent Booker", "Artist Manager")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            roles
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        roleSpinner.adapter = adapter

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("user_username", "")
        val savedEmail = prefs.getString("user_email", "")
        val savedPhone = prefs.getString("user_phone", "")
        val savedRole = prefs.getString("user_role", null)
        val savedImageUri = prefs.getString("user_profile_image_uri", null)

        usernameEditText.setText(savedUsername)
        emailEditText.setText(savedEmail)
        phoneEditText.setText(savedPhone)

        if (savedRole != null) {
            val index = roles.indexOf(savedRole)
            if (index >= 0) {
                roleSpinner.setSelection(index)
            }
        }

        if (!savedImageUri.isNullOrEmpty()) {
            val uri = Uri.parse(savedImageUri)
            selectedImageUri = uri
            profileImageView.setImageURI(uri)
        }

        // Tap image to change photo
        profileImageView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        saveButton.setOnClickListener {
            val newUsername = usernameEditText.text.toString().trim()
            val newEmail = emailEditText.text.toString().trim()
            val newPhone = phoneEditText.text.toString().trim()
            val selectedRole = roleSpinner.selectedItem?.toString() ?: ""

            if (newUsername.isEmpty()) {
                Toast.makeText(this, "Please enter a username.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (newEmail.isEmpty()) {
                Toast.makeText(this, "Please enter an email address.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            prefs.edit().apply {
                // username and email used elsewhere (login, callback)
                putString("user_username", newUsername)
                putString("user_email", newEmail)
                putString("user_phone", newPhone)
                putString("user_role", selectedRole)
                selectedImageUri?.let { putString("user_profile_image_uri", it.toString()) }
            }.apply()

            Toast.makeText(this, "Profile saved.", Toast.LENGTH_LONG).show()
        }
    }
}
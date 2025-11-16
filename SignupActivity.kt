package com.example.backstagepass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText: EditText = findViewById(R.id.editUsernameText)
        val emailEditText: EditText = findViewById(R.id.editEmailText)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val signUpButton: Button = findViewById(R.id.button2)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in username, email and password.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            prefs.edit()
                .putString("user_username", username)
                .putString("user_email", email)
                .putString("user_password", password)
                .apply()

            Toast.makeText(this, "Account created. You can log in now.", Toast.LENGTH_LONG).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
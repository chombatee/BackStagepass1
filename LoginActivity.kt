package com.example.backstagepass

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText: EditText = findViewById(R.id.editText)
        val passwordEditText: EditText = findViewById(R.id.editTextPassword)
        val loginButton: Button = findViewById(R.id.button2)
        val createAccountText: TextView = findViewById(R.id.textView3)

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString().trim()
            val enteredPassword = passwordEditText.text.toString().trim()

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please enter username and password.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val savedUsername = prefs.getString("user_username", null)
            val savedPassword = prefs.getString("user_password", null)

            if (savedUsername == null || savedPassword == null) {
                Toast.makeText(this, "No account found. Please sign up first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Incorrect username or password.", Toast.LENGTH_LONG).show()
            }
        }

        createAccountText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
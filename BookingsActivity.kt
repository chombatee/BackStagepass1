package com.example.backstagepass

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BookingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bookings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val artistNameFromDetail = intent.getStringExtra("artistName") ?: "Selected Artist"

        val textArtist: TextView = findViewById(R.id.textBookingArtist)
        val editEventDate: EditText = findViewById(R.id.editEventDate)
        val editEventLocation: EditText = findViewById(R.id.editEventLocation)
        val spinnerEventType: Spinner = findViewById(R.id.spinnerEventType)
        val buttonRequestCallback: Button = findViewById(R.id.buttonRequestCallback)

        textArtist.text = "Artist: $artistNameFromDetail"

        val eventTypes = listOf("Concert", "Festival", "Corporate Event")
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            eventTypes
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerEventType.adapter = adapter

        buttonRequestCallback.setOnClickListener {
            val date = editEventDate.text.toString().trim()
            val location = editEventLocation.text.toString().trim()
            val type = spinnerEventType.selectedItem?.toString() ?: ""

            if (date.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "Please fill in date and location.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val email = prefs.getString("user_email", "") ?: ""

            if (email.isEmpty()) {
                Toast.makeText(this, "No email found. Please sign up first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val subject = "Booking Callback Request"
            val body = """
                Hi,
                
                I would like a callback about booking:

                Artist: $artistNameFromDetail
                Event date: $date
                Event location: $location
                Event type: $type

                Thank you.
            """.trimIndent()

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, body)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(intent, "Send confirmation email"))
            } else {
                Toast.makeText(this, "No email app installed.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
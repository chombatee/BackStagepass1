package com.example.backstagepass

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.imageview.ShapeableImageView

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val artistName = intent.getStringExtra("artistName") ?: ""
        val artistGenre = intent.getStringExtra("artistGenre") ?: ""
        val artistImageRes = intent.getIntExtra("artistImage", 0)

        val bigImage: ShapeableImageView = findViewById(R.id.posterBigImg)
        val normalImage: ShapeableImageView = findViewById(R.id.posterNormalImg)
        val nameText: TextView = findViewById(R.id.textArtistName)
        val genreText: TextView = findViewById(R.id.textArtistGenre)
        val infoText: TextView = findViewById(R.id.textArtistInfo)

        if (artistImageRes != 0) {
            bigImage.setImageResource(artistImageRes)
            normalImage.setImageResource(artistImageRes)
        }
        nameText.text = artistName
        genreText.text = artistGenre

        // Info card content per artist
        val info = when (artistName.lowercase()) {
            "uncle waffles" -> """
Uncle Waffles — Swazi Amapiano DJ and record producer

- Spotify — monthly listeners: ~1,489,028
- Instagram: ~3.0M followers (@unclewaffles_)
- TikTok: ~3.5M followers
- YouTube: ~505K subscribers (~1.4–1.5M monthly listeners)
""".trimIndent()

            "tyla" -> """
Tyla — South African singer and songwriter (pop / amapiano fusion)

- Spotify — monthly listeners: ~28–30 million
- Instagram: ~12M followers (@tyla)
- TikTok — ~14.6M followers
""".trimIndent()

            "nanette" -> """
Nanette — South African R&B artist (Nanette Mbili)

- Spotify — monthly listeners: ~77k–80k
- Instagram: ~35k followers (@officialnanette)
- YouTube — ~11.5–11.6K subscribers (NanetteOfficial)
""".trimIndent()

            else -> ""
        }

        if (info.isNotEmpty()) {
            infoText.text = info
            infoText.visibility = View.VISIBLE
        } else {
            infoText.visibility = View.GONE
        }

        // Back arrow in the top bar
        val backButton: ImageView = findViewById(R.id.imageView5)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Book Artist button
        val bookButton: Button = findViewById(R.id.buttonBookArtist)
        bookButton.setOnClickListener {
            val intent = Intent(this, BookingsActivity::class.java).apply {
                putExtra("artistName", artistName)
            }
            startActivity(intent)
        }
    }
}
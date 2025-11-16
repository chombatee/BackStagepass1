package com.example.backstagepass

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var topArtistsRecycler: RecyclerView
    private lateinit var genresRecycler: RecyclerView

    private lateinit var allArtists: List<Artist>
    private lateinit var artistAdapter: ArtistAdapter
    private var currentGenreFilter: String? = null
    private var currentSearchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDashboardLists()
        setupSearchBar()
        setupBottomNavigation()
    }

    private fun setupDashboardLists() {
        topArtistsRecycler = findViewById(R.id.view1)
        genresRecycler = findViewById(R.id.textView2)

        topArtistsRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        genresRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        allArtists = listOf(
            Artist("Uncle Waffles", R.drawable.uncle_waffles, "Amapiano"),
            Artist("Tyla", R.drawable.tyla_a, "RnB"),
            Artist("Nanette", R.drawable.nanette1, "RnB"),
             Artist("DBN Gogo", R.drawable.dbn1gogo, "Amapiano"),
            Artist("Kelvin Momo", R.drawable.mo_mo, "Amapiano"),
            Artist("Maglera Doe Boy", R.drawable.maglera_, "Hip Hop")
        )

        val genres = listOf(
            Genre("Amapiano", R.drawable.waffles_uncle),
            Genre("Hip-Hop", R.drawable.maglera_),
            Genre("RnB", R.drawable.tyla_pink)
        )

        artistAdapter = ArtistAdapter(allArtists) { artist ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("artistName", artist.name)
                putExtra("artistImage", artist.imageResId)
                putExtra("artistGenre", artist.genre)
            }
            startActivity(intent)
        }
        topArtistsRecycler.adapter = artistAdapter

        genresRecycler.adapter = GenreAdapter(genres) { genre ->
            onGenreSelected(genre)
        }
    }

    private fun onGenreSelected(genre: Genre) {
        currentGenreFilter = if (currentGenreFilter == genre.name) {
            // tap same genre again → show all artists
            null
        } else {
            genre.name
        }
        applyFilters()
    }

    private fun setupSearchBar() {
        val searchEditText: EditText = findViewById(R.id.editTextText)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentSearchQuery = s?.toString()?.trim() ?: ""
                applyFilters()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun applyFilters() {
        var filtered = allArtists

        currentGenreFilter?.let { genreName ->
            val normalizedGenre = genreName.lowercase().replace("-", "").replace(" ", "")
            filtered = filtered.filter { artist ->
                val artistGenreNorm = artist.genre.lowercase().replace("-", "").replace(" ", "")
                artistGenreNorm == normalizedGenre
            }
        }

        if (currentSearchQuery.isNotEmpty()) {
            filtered = filtered.filter { artist ->
                artist.name.contains(currentSearchQuery, ignoreCase = true)
            }
        }

        artistAdapter.updateItems(filtered)
    }

    private fun setupBottomNavigation() {
        val profileButton: ImageView = findViewById(R.id.imageView3)
        val rosterButton: ImageView = findViewById(R.id.imageView2)
        val bookingsButton: ImageView = findViewById(R.id.imageView4)

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        rosterButton.setOnClickListener {
            val scrollView: ScrollView? = findViewById(R.id.scrollView2)
            scrollView?.smoothScrollTo(0, 0)
        }

        bookingsButton.setOnClickListener {
            val intent = Intent(this, BookingsActivity::class.java)
            startActivity(intent)
        }
    }

    data class Artist(val name: String, val imageResId: Int, val genre: String)
    data class Genre(val name: String, val imageResId: Int)

    private class ArtistAdapter(
        private var items: List<Artist>,
        private val onItemClick: (Artist) -> Unit
    ) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

        class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val image: ImageView = itemView.findViewById(R.id.artistImage)
            val name: TextView = itemView.findViewById(R.id.artistName)
        }

        fun updateItems(newItems: List<Artist>) {
            items = newItems
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_artist, parent, false)
            return ArtistViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
            val item = items[position]
            holder.image.setImageResource(item.imageResId)
            holder.name.text = item.name
            holder.itemView.setOnClickListener { onItemClick(item) }
        }

        override fun getItemCount(): Int = items.size
    }

    private class GenreAdapter(
        private val items: List<Genre>,
        private val onItemClick: (Genre) -> Unit
    ) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

        class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.genreName)
            val image: ImageView = itemView.findViewById(R.id.genreImage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_genre, parent, false)
            return GenreViewHolder(view)
        }

        override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name
            holder.image.setImageResource(item.imageResId)

            val matrix = ColorMatrix()
            matrix.setSaturation(0f)
            holder.image.colorFilter = ColorMatrixColorFilter(matrix)

            holder.itemView.setOnClickListener { onItemClick(item) }
        }

        override fun getItemCount(): Int = items.size
    }
}
package com.example.primeiroappshare.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.primeiroappshare.databinding.ActivityDetailsMovieBinding
import com.example.primeiroappshare.model.GenreModel
import com.example.primeiroappshare.model.MovieModel
import com.example.primeiroappshare.model.MovieRepository
import com.example.primeiroappshare.view.MainActivity.Companion.ID_LIST

class DetailsMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsMovieBinding

    companion object {
        const val ID_MOVIE = "id_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(ID_MOVIE, -1)
        val idList = intent.getIntExtra(ID_LIST, -1)

        callDetails(id)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, ListMoviesActivity::class.java)
            when (idList) {
                0 -> intent.putExtra(ID_LIST, idList)
                1 -> intent.putExtra(ID_LIST, idList)
                2 -> intent.putExtra(ID_LIST, idList)
                3 -> intent.putExtra(ID_LIST, idList)
                else -> Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show()
            }
            startActivity(intent)
        }
    }

    private fun getFormattedGenre(movie: MovieModel): String{
        var textGenre = ""
        movie.genres?.forEachIndexed{index, name ->
            if(index == 0) textGenre += name
            else textGenre += ", $name"
        }
        return textGenre
    }

    private fun callDetails(id: Int) {
        MovieRepository.getMovie({
            binding.progressBar.visibility = View.GONE
            binding.scrollViewDetails.visibility = View.VISIBLE
            binding.nameMovie.text = it.title
            binding.imdbMovie.text = "IMDb: ${it.vote_average}"
            binding.adultMovie.text = if (it.adult) "Age: +18" else "Age: -18"
            val releaseYear: String = it.release_date.take(4)
            binding.releaseYear.text = "Year: ${releaseYear}"
            binding.durationMovie.text = "Duration: ${it.runtime}min"
            binding.genreMovie.text = "Genre: ${getFormattedGenre(it)}"
            binding.overviewMovie.text = it.overview
            binding.btnBack.visibility = View.VISIBLE
            binding.posterMovie.visibility = View.VISIBLE
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${it.poster_path}")
                .into(binding.posterMovie)
        }, id)
    }
}




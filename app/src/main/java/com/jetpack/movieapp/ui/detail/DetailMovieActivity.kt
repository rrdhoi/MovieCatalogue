package com.jetpack.movieapp.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jetpack.movieapp.R
import com.jetpack.movieapp.data.MovieDetail
import com.jetpack.movieapp.databinding.ActivityDetailMovieBinding
import com.jetpack.movieapp.databinding.ContentDetailMovieBinding
import com.jetpack.movieapp.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailMovieActivity : AppCompatActivity(), KodeinAware {
    private lateinit var bindingDetailActivity: ActivityDetailMovieBinding
    private lateinit var bindingDetailContent: ContentDetailMovieBinding

    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingDetailActivity = ActivityDetailMovieBinding.inflate(layoutInflater)
        bindingDetailContent = bindingDetailActivity.detailMovie

        setContentView(bindingDetailActivity.root)
        setSupportActionBar(bindingDetailActivity.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val proCompaniesAdapter = ProductionCompaniesAdapter()

        val detailViewModel = ViewModelProvider(this, factory)[DetailMovieViewModel::class.java]

        bindingDetailActivity.nestedScrollView.visibility = View.INVISIBLE
        bindingDetailActivity.circularProgressBar.visibility = View.VISIBLE

        val getMovieId = intent.getIntExtra(EXTRA_MOVIE, 0)

        detailViewModel.setSelectedMovie(getMovieId)
        detailViewModel.getMovie().observe(this@DetailMovieActivity, {
            bindingDetailActivity.circularProgressBar.visibility = View.GONE
            bindingDetailActivity.nestedScrollView.visibility = View.VISIBLE

            populateMovie(it)
        })

        bindingDetailContent.progressBar.visibility = View.VISIBLE
        bindingDetailContent.rvProductionCompanies.visibility = View.INVISIBLE
        detailViewModel.getProCompanies().observe(this@DetailMovieActivity, {
            proCompaniesAdapter.setModules(it)
            proCompaniesAdapter.notifyDataSetChanged()

            bindingDetailContent.progressBar.visibility = View.GONE
            bindingDetailContent.rvProductionCompanies.visibility = View.VISIBLE
        })

        with(bindingDetailContent.rvProductionCompanies) {
            layoutManager =
                LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = proCompaniesAdapter
        }
    }

    private fun populateMovie(movie: MovieDetail) {
        with(bindingDetailContent) {
            textTitle.text = movie.title
            textDescription.text = movie.description

            Glide.with(this@DetailMovieActivity)
                .asBitmap()
                .load(movie.imagePath)
                .transform(RoundedCorners(20))
                .load("https://image.tmdb.org/t/p/w500" + movie.imagePath)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        imagePoster.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        imagePoster.background = placeholder
                    }
                })
        }
    }

}
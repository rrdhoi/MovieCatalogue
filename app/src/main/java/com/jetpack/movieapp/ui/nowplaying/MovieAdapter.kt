package com.jetpack.movieapp.ui.nowplaying

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jetpack.movieapp.R
import com.jetpack.movieapp.data.MovieNowPlaying
import com.jetpack.movieapp.databinding.ItemsNowplayingBinding
import com.jetpack.movieapp.ui.detail.DetailMovieActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movieList = ArrayList<MovieNowPlaying>()

    fun setDataCourses(movie: List<MovieNowPlaying>?) {
        if (movie == null) {
            return
        }
        this.movieList.clear()
        this.movieList.addAll(movie)
    }

    class MovieViewHolder(private val binding: ItemsNowplayingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieNowPlaying) {
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemVoteAverage.text = movie.voteAverage.toString()

                Glide.with(itemView.context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + movie.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            imgPoster.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            imgPoster.background = placeholder
                        }
                    })

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.movieId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding: ItemsNowplayingBinding =
            ItemsNowplayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
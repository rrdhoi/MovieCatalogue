package com.jetpack.movieapp.ui.upcoming

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
import com.jetpack.movieapp.data.MovieUpComing
import com.jetpack.movieapp.databinding.ItemsUpcomingBinding
import com.jetpack.movieapp.ui.detail.DetailMovieActivity

class TvshowAdapter(private val callback: TvshowFragmentCallback): RecyclerView.Adapter<TvshowAdapter.TvshowViewHolder>() {
    private var movieList = ArrayList<MovieUpComing>()

    fun setDataList(movies: List<MovieUpComing>?){
        if (movies == null) {
            return
        }
        this.movieList.clear()
        this.movieList.addAll(movies)
    }
    
    inner class TvshowViewHolder(private val binding: ItemsUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieUpComing){
            with(binding) {
                tvItemTitle.text = movie.title
                tvItemVoteAverage.text = movie.voteAverage.toString()

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie.movieId)
                    itemView.context.startActivity(intent)
                }

                imgShare.setOnClickListener {
                    callback.onSharedClick(movie)
                }

                Glide.with(itemView.context)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500" + movie.imagePath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(object: CustomTarget<Bitmap>(){
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
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvshowAdapter.TvshowViewHolder {
        val binding = ItemsUpcomingBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TvshowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvshowAdapter.TvshowViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

}
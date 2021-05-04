package com.jetpack.movieapp.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.jetpack.movieapp.R
import com.jetpack.movieapp.data.ProCompanies
import com.jetpack.movieapp.databinding.ItemsActorsBinding

class ProductionCompaniesAdapter: RecyclerView.Adapter<ProductionCompaniesAdapter.ProCompaniesHolder>() {
    private val listProCompanies = ArrayList<ProCompanies>()

    fun setModules(modules: List<ProCompanies>?) {
        if (modules == null) return
        this.listProCompanies.clear()
        this.listProCompanies.addAll(modules)
    }

    inner class ProCompaniesHolder(private val binding: ItemsActorsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(proCompany: ProCompanies) {
            binding.tvName.text = proCompany.name

            Glide.with(itemView.context)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w500" + proCompany.imagePath)
                .transform(RoundedCorners(50))
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(object: CustomTarget<Bitmap>(){
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        binding.ivProCompany.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        binding.ivProCompany.background = placeholder
                    }
                })

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, proCompany.name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProCompaniesHolder {
        val itemProCompaniesBinding = ItemsActorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProCompaniesHolder(itemProCompaniesBinding)
    }

    override fun onBindViewHolder(holder: ProCompaniesHolder, position: Int) {
        val proCompany = listProCompanies[position]
        holder.bind(proCompany)
    }

    override fun getItemCount(): Int = listProCompanies.size
}
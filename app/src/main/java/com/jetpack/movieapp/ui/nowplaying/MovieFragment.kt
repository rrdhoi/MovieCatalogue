package com.jetpack.movieapp.ui.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetpack.movieapp.databinding.FragmentMovieBinding
import com.jetpack.movieapp.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieFragment : Fragment(), KodeinAware {

    private lateinit var binding: FragmentMovieBinding
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val movieAdapter = MovieAdapter()

        binding.progressBar.visibility = View.VISIBLE

        this.lifecycleScope.launchWhenStarted {
            viewModel.getMovie().observe(viewLifecycleOwner, { movie ->
                binding.progressBar.visibility = View.GONE

                movieAdapter.setDataCourses(movie)
                movieAdapter.notifyDataSetChanged()
            })
        }

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }


}
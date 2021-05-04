package com.jetpack.movieapp.ui.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jetpack.movieapp.R
import com.jetpack.movieapp.data.MovieUpComing
import com.jetpack.movieapp.databinding.FragmentTvshowBinding
import com.jetpack.movieapp.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class TvshowFragment : Fragment(), TvshowFragmentCallback, KodeinAware {
    private lateinit var binding: FragmentTvshowBinding
    override val kodein: Kodein by kodein()
    private val factory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowAdapter = TvshowAdapter(this)

        binding.progressBar.visibility = View.VISIBLE

        this.lifecycleScope.launchWhenStarted {
            viewModel.getTvShow().observe(viewLifecycleOwner, {
                binding.progressBar.visibility = View.GONE

                tvShowAdapter.setDataList(it)
                tvShowAdapter.notifyDataSetChanged()
            })
        }

        with(binding.rvTvshow) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowAdapter
        }
    }

    override fun onSharedClick(movie: MovieUpComing) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder
            .from(requireActivity())
            .setType(mimeType)
            .setChooserTitle("Mau di bagikan kemana sayang?.")
            .setText(resources.getString(R.string.share_text, movie.title))
            .startChooser()
    }
}
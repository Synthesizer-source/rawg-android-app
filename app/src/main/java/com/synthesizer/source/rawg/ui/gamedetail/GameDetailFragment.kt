package com.synthesizer.source.rawg.ui.gamedetail

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.ui.BaseFragment
import com.synthesizer.source.rawg.utils.EventObserver
import com.synthesizer.source.rawg.utils.convertToDate
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GameDetailFragment : BaseFragment() {

    @Inject
    lateinit var gameDetailViewModelFactory: GameDetailViewModel.AssistedFactory

    private val args: GameDetailFragmentArgs by navArgs()

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    override val viewModel: GameDetailViewModel by viewModels {
        GameDetailViewModel.provideFactory(gameDetailViewModelFactory, args.gameId)
    }

    private var _ratingAnimator: ValueAnimator? = null

    private var _adapter: GameDetailScreenshotAdapter? = null
    private val adapter get() = _adapter!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        _adapter = GameDetailScreenshotAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    override fun onPause() {
        super.onPause()
        _adapter = null
        binding.screenshots.adapter = null
        _ratingAnimator?.let {
            if (it.isRunning) it.pause()
            it.removeAllUpdateListeners()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ratingAnimator = null
        _binding = null
    }

    private fun observe() {
        binding.screenshots.adapter = adapter

        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver {
            binding.apply {
                val state = if (it) View.INVISIBLE else View.VISIBLE
                val loadingIconState = if (it) View.VISIBLE else View.GONE
                loadingIcon.visibility = loadingIconState
                infoLayer.visibility = state
                background.visibility = state
                rating.visibility = state
                pcPlatformIcon.visibility = state
                psPlatformIcon.visibility = state
                xboxPlatformIcon.visibility = state
                nintendoPlatformIcon.visibility = state
                descriptionLabel.visibility = state
                description.visibility = state
            }
        })

        viewModel.screenshotsVisibility.observe(viewLifecycleOwner, EventObserver {
            binding.screenshotsLabel.setVisibility(it)
            binding.screenshots.setVisibility(it)
        })

        viewModel.screenshots.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.gameDetail.observe(viewLifecycleOwner, {
            binding.apply {
                background.loadImage(it.backgroundImage)
                name.text = it.name
                releaseDate.text = it.releaseDate.convertToDate()
                publisherName.text = it.publisher
                it.platforms.map { p -> showPlatform(p) }
                setMetascore(it.metascore)
                createGenreChips(it.genres)
                description.setBodyContent(it.description)
                animateRatingBar(it.rating)
            }
        })
    }

    private fun showPlatform(platform: String) {
        binding.apply {
            when (platform) {
                "pc" -> pcPlatformIcon.setVisibility(true)
                "playstation" -> psPlatformIcon.setVisibility(true)
                "xbox" -> xboxPlatformIcon.setVisibility(true)
                "nintendo" -> nintendoPlatformIcon.setVisibility(true)
            }
        }
    }

    private fun animateRatingBar(rating: Double) {
        val duration = 500L
        _ratingAnimator = ValueAnimator.ofFloat(0.00f, rating.toFloat())
        _ratingAnimator!!.duration = duration
        _ratingAnimator!!.addUpdateListener {
            val currentValue = it.animatedValue as Float
            binding.rating.rating = currentValue
        }

        _ratingAnimator!!.start()
    }

    private fun setMetascore(score: Int) {
        binding.apply {
            metascore.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    viewModel.metascoreColor
                )
            )
            metascore.setStrokeColorResource(viewModel.metascoreColor)
            metascore.text = score.toString()
        }
    }

    private fun createGenreChips(genres: List<String>) {
        genres.forEach {
            val chip = Chip(requireContext())
            chip.text = it
            binding.genreChipGroup.addView(chip)
        }
    }
}
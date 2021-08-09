package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.utils.convertToDate
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    @Inject
    lateinit var gameDetailViewModelFactory: GameDetailViewModel.AssistedFactory

    private val args: GameDetailFragmentArgs by navArgs()

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameDetailViewModel by viewModels {
        GameDetailViewModel.provideFactory(gameDetailViewModelFactory, args.gameId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {

        viewModel.isLoading.observe(viewLifecycleOwner, {
            binding.apply {
                val state = if (it) View.INVISIBLE else View.VISIBLE
                val loadingIconState = if (it) View.VISIBLE else View.GONE
                loadingIcon.visibility = loadingIconState
                background.visibility = state
                name.visibility = state
                releaseDateLabel.visibility = state
                releaseDate.visibility = state
                publisherNameLabel.visibility = state
                publisherName.visibility = state
                ratingLabel.visibility = state
                rating.visibility = state
                pcPlatformIcon.visibility = state
                psPlatformIcon.visibility = state
                xboxPlatformIcon.visibility = state
                nintendoPlatformIcon.visibility = state
                descriptionLabel.visibility = state
                description.visibility = state
            }
        })

        viewModel.gameDetail.observe(viewLifecycleOwner, {
            binding.apply {
                background.loadImage(it.backgroundImage)
                name.text = it.name
                releaseDate.text = it.releaseDate.convertToDate()
                rating.text = it.rating.toString()
                publisherName.text = it.publisher
                it.platforms.map { p -> showPlatform(p) }
                description.setBodyContent(it.description)
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
}
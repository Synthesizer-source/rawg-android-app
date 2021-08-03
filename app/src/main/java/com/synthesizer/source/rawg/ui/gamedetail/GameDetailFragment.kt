package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.repository.GameDetailRepository
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setVisibility

class GameDetailFragment : Fragment() {

    private val args: GameDetailFragmentArgs by navArgs()

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameDetailViewModel by viewModels {
        GameDetailViewModelFactory(args.gameId, GameDetailRepository())
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
        viewModel.gameDetail.observe(viewLifecycleOwner, {
            binding.apply {
                gameBackground.loadImage(it.background_image)
                gameName.text = it.name
                gameReleaseDate.text = it.released
                gameMetacritic.text = it.metacritic.toString()
                gamePublisherName.text = it.publishers[0].name
                it.parent_platforms.map { p -> showPlatform(p.platform.slug) }
                gameDescription.text = it.description_raw
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPlatform(platform: String) {
        binding.apply {
            when (platform) {
                "pc" -> pcPlatformIcon.setVisibility(true)
                "ps" -> psPlatformIcon.setVisibility(true)
                "xbox" -> xboxPlatformIcon.setVisibility(true)
                "nintendo" -> nintendoPlatformIcon.setVisibility(true)
            }
        }
    }
}
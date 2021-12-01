package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.ui.BaseFragment
import com.synthesizer.source.rawg.utils.EventObserver
import com.synthesizer.source.rawg.utils.addPlatformIcons
import com.synthesizer.source.rawg.utils.hideChildren
import com.synthesizer.source.rawg.utils.load
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setMetascore
import com.synthesizer.source.rawg.utils.setVisibility
import com.synthesizer.source.rawg.utils.showChildren
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

    private val adapter by lazy { GameDetailScreenshotAdapter() }

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
        binding.screenshots.adapter = adapter
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver {
            binding.constraintLayout.apply { if (it) hideChildren() else showChildren() }
            binding.loadingIcon.setVisibility(it)
        })

        viewModel.screenshots.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.gameDetail.observe(viewLifecycleOwner, {
            binding.apply {
                background.loadImage(it.backgroundImage)
                name.text = it.name
                releaseDate.text = it.releaseDate
                publisherName.text = it.publisher
                platforms.addPlatformIcons(icons = viewModel.getPlatformIcons())
                metascore.setMetascore(it.metascore, viewModel.metascoreColor)
                genreChipGroup.load(it.genres)
                description.setBodyContent(it.description)
                rating.startAnimation(it.rating)
            }
        })
    }
}
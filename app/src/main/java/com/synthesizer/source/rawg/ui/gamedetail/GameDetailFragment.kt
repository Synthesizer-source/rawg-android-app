package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.ui.BaseFragment
import com.synthesizer.source.rawg.utils.addPlatformIcons
import com.synthesizer.source.rawg.utils.hideChildren
import com.synthesizer.source.rawg.utils.load
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setMetascore
import com.synthesizer.source.rawg.utils.setVisibility
import com.synthesizer.source.rawg.utils.showChildren
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameDetailFragment : BaseFragment() {

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    override val viewModel: GameDetailViewModel by viewModels()

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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.filterNotNull().collect {
                        binding.apply {
                            if (it) constraintLayout.hideChildren()
                            else constraintLayout.showChildren()
                            loadingIcon.setVisibility(it)
                        }
                    }
                }
                launch {
                    viewModel.detail.filterNotNull().collect {
                        binding.apply {
                            background.loadImage(it.backgroundImage)
                            name.text = it.name
                            releaseDate.text = it.releaseDate
                            publisherName.text = it.publisher
                            platforms.addPlatformIcons(icons = viewModel.getPlatformIcons())
                            metascore.setMetascore(
                                it.metascore,
                                viewModel.metascoreColor
                            )
                            genreChipGroup.load(it.genres)
                            description.setBodyContent(it.description)
                            rating.startAnimation(it.rating)
                        }
                    }
                }

                launch {
                    viewModel.screenshots.filterNotNull().collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }
}
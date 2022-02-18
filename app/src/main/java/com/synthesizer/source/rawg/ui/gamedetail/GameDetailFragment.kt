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
import com.synthesizer.source.rawg.utils.gone
import com.synthesizer.source.rawg.utils.hideChildren
import com.synthesizer.source.rawg.utils.load
import com.synthesizer.source.rawg.utils.loadImage
import com.synthesizer.source.rawg.utils.setMetascore
import com.synthesizer.source.rawg.utils.showChildren
import com.synthesizer.source.rawg.utils.visible
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
                    viewModel.detail.filterNotNull().collect {
                        val viewState = GameDetailViewState(it.detail)
                        val detail = it.detail
                        val screenshots = it.screenshots
                        binding.apply {
                            background.loadImage(detail.backgroundImage)
                            name.text = detail.name
                            releaseDate.text = detail.releaseDate
                            publisherName.text = detail.publisher
                            platforms.addPlatformIcons(icons = viewState.getPlatformIcons())
                            metascore.setMetascore(
                                detail.metascore,
                                viewState.getMetascoreColor()
                            )
                            genreChipGroup.load(detail.genres)
                            description.setBodyContent(detail.description)
                            rating.startAnimation(detail.rating)
                            adapter.submitList(screenshots)
                        }
                    }
                }
            }
        }
    }

    override fun onLoading() {
        super.onLoading()
        binding.apply {
            constraintLayout.hideChildren()
            loadingIcon.visible()
        }
    }

    override fun onLoaded() {
        super.onLoaded()
        binding.apply {
            constraintLayout.showChildren()
            loadingIcon.gone()
        }
    }
}
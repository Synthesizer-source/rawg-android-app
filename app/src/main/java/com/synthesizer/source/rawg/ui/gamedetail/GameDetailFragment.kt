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
import com.synthesizer.source.rawg.ui.gamedetail.component.description.DescriptionUIModel
import com.synthesizer.source.rawg.ui.gamedetail.component.screenshot.ScreenshotUIModel
import com.synthesizer.source.rawg.ui.gamedetail.component.summary.SummaryUIModel
import com.synthesizer.source.rawg.utils.gone
import com.synthesizer.source.rawg.utils.hideChildren
import com.synthesizer.source.rawg.utils.loadImage
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.filterNotNull().collect {
                        val detail = it.detail.info
                        val screenshots = it.detail.screenshots
                        binding.apply {
                            background.loadImage(detail.backgroundImage)
                            summary.initialize(
                                SummaryUIModel(
                                    gameName = detail.name,
                                    releaseDate = detail.releaseDate,
                                    publisherName = detail.publisher,
                                    rating = detail.rating,
                                    platforms = detail.platforms,
                                    metascore = detail.metascore,
                                    genres = detail.genres
                                )
                            )
                            description.initialize(
                                DescriptionUIModel(
                                    description = detail.description
                                )
                            )
                            screenshot.initialize(
                                ScreenshotUIModel(
                                    screenshots = screenshots
                                )
                            )
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
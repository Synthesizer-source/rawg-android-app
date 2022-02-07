package com.synthesizer.source.rawg.ui.gamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.synthesizer.source.rawg.core.UIState
import com.synthesizer.source.rawg.databinding.FragmentGameListBinding
import com.synthesizer.source.rawg.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameListFragment : BaseFragment() {

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!

    override val viewModel: GameListViewModel by viewModels()

    private val adapter = GameListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = _binding ?: FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameList.adapter = adapter
        adapter.itemClickListener = {
            navigateToGameDetail(it)
        }
        adapter.firstItemLoadedListener = {
            binding.loadingIcon.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.filterNotNull().collect {
                        when (it) {
                            is UIState.Error -> viewModel.error(it.throwable) {
                                viewModel.fetchGames()
                            }
                            UIState.Loading -> {
                                binding.loadingIcon.visibility = View.VISIBLE
                                binding.gameList.visibility = View.INVISIBLE
                            }
                            is UIState.Success -> {

                                adapter.submitData(lifecycle, it.data)
                            }
                        }
                    }
                }

                launch {
                    adapter.loadStateFlow.collectLatest { state ->
                        if (state.refresh is LoadState.Error) {
                            val throwable = (state.refresh as LoadState.Error).error
                            viewModel.error(throwable) {
                                viewModel.fetchGames()
                            }
                        } else if (state.append is LoadState.Loading) {
                            onLoaded()
                        } else if (state.append is LoadState.NotLoading
                            && state.append.endOfPaginationReached
                            && adapter.itemCount == 0
                        ) {
//                            onEmpty()
                        }
                    }
                }
            }
        }
    }

    private fun onLoaded() {
        binding.loadingIcon.visibility = View.GONE
        binding.gameList.visibility = View.VISIBLE
    }

    private fun navigateToGameDetail(id: Int) {
        val action = GameListFragmentDirections.showGameDetail(id)
        findNavController().navigate(action)
    }
}
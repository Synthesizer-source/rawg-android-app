package com.synthesizer.source.rawg.ui.gamelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.databinding.FragmentGameListBinding
import com.synthesizer.source.rawg.ui.BaseFragment
import com.synthesizer.source.rawg.utils.EventObserver
import com.synthesizer.source.rawg.utils.setVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GameListFragment : BaseFragment() {

    @Inject
    lateinit var gameListViewModelFactory: GameListViewModel.AssistedFactory

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!

    private val args: GameListFragmentArgs by navArgs()

    override val viewModel: GameListViewModel by viewModels {
        GameListViewModel.provideFactory(
            assistedFactory = gameListViewModelFactory,
            search = args.search,
            ordering = args.ordering,
            dates = args.dates
        )
    }

    private val adapter = GameListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameList.adapter = adapter
        adapter.itemClickListener = {
            navigateToGameDetail(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner, EventObserver {
            binding.loadingIcon.setVisibility(it)
            binding.gameList.setVisibility(!it)
        })
        viewModel.games.observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                viewModel.loadStates(loadStates)
            }
        }
    }

    private fun navigateToGameDetail(id: Int) {
        val action = GameListFragmentDirections.showGameDetail(id)
        findNavController().navigate(action)
    }
}
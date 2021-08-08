package com.synthesizer.source.rawg.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synthesizer.source.rawg.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val gamesAdapter = GamesAdapter()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.games.observe(viewLifecycleOwner, {
            gamesAdapter.submitData(lifecycle, it)
        })

        gamesAdapter.itemClickListener = { id ->
            navigateToGameDetail(id)
        }

        binding.games.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = gamesAdapter
        }
    }

    private fun navigateToGameDetail(id: Int) {
        val action = HomeFragmentDirections.showGameDetail(id)
        findNavController().navigate(action)
    }
}
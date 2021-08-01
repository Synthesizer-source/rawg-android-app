package com.synthesizer.source.rawg.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GamesRemote
import com.synthesizer.source.rawg.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private val gamesAdapter = GamesAdapter()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesAdapter.itemClickListener = { id ->
            navigateToGameDetail(id)
        }

        api.getGames().enqueue(object : Callback<GamesRemote> {
            override fun onResponse(call: Call<GamesRemote>, response: Response<GamesRemote>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        gamesAdapter.submitList(it.results)
                    }
                }
            }

            override fun onFailure(call: Call<GamesRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onResponse: ${t.message}")
            }
        })

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
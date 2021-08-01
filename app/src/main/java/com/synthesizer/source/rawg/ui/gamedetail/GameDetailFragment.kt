package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.databinding.FragmentGameDetailBinding
import com.synthesizer.source.rawg.utils.loadImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailFragment : Fragment() {

    val args: GameDetailFragmentArgs by navArgs()

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        api.getGameDetailById(args.gameId).enqueue(object : Callback<GameDetailRemote> {
            override fun onResponse(
                call: Call<GameDetailRemote>,
                response: Response<GameDetailRemote>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()!!

                    binding.apply {
                        gameBackground.loadImage(body.background_image)
                        gameName.text = body.name
                        gameReleaseDate.text = body.released
                        gameMetacritic.text = body.metacritic.toString()
                        gamePublisherName.text = body.publishers[0].name
                        val platforms =
                            body.parent_platforms.map { it.platform.name }.joinToString { it }
                        gamePlatforms.text = platforms
                        gameDescription.text = body.description_raw
                    }
                }
            }

            override fun onFailure(call: Call<GameDetailRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onFailure: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
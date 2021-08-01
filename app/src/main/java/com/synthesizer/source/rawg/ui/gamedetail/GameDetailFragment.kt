package com.synthesizer.source.rawg.ui.gamedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GameDetailRemote
import com.synthesizer.source.rawg.utils.loadImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailFragment : Fragment() {

    val args: GameDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_detail, container, false)

        api.getGameDetailById(args.gameId).enqueue(object : Callback<GameDetailRemote> {
            override fun onResponse(
                call: Call<GameDetailRemote>,
                response: Response<GameDetailRemote>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()!!
                    view.findViewById<ImageView>(R.id.gameBackground)
                        .loadImage(body.background_image)
                    view.findViewById<TextView>(R.id.gameName).text = body.name
                    view.findViewById<TextView>(R.id.gameReleaseDate).text = body.released
                    view.findViewById<TextView>(R.id.gameMetacritic).text =
                        body.metacritic.toString()
                    view.findViewById<TextView>(R.id.gamePublisherName).text =
                        body.publishers[0].name
                    val platforms =
                        body.parent_platforms.map { it.platform.name }.joinToString { it }
                    view.findViewById<TextView>(R.id.gamePlatforms).text = platforms
                    view.findViewById<TextView>(R.id.gameDescription).text = body.description_raw
                }
            }

            override fun onFailure(call: Call<GameDetailRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onFailure: ${t.message}")
            }

        })

        return view
    }
}
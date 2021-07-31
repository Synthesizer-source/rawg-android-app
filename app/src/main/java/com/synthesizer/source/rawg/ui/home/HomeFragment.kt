package com.synthesizer.source.rawg.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.api.api
import com.synthesizer.source.rawg.data.remote.GamesRemote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    val gamesAdapter = GamesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        api.getGames().enqueue(object : Callback<GamesRemote> {
            override fun onResponse(call: Call<GamesRemote>, response: Response<GamesRemote>) {
                if (response.isSuccessful) {
                    Log.d("synthesizer-source", "onResponse: ${response.body().toString()}")
                }
            }

            override fun onFailure(call: Call<GamesRemote>, t: Throwable) {
                Log.d("synthesizer-source", "onResponse: ${t.message}")
            }

        })
        val rcyc = view.findViewById<RecyclerView>(R.id.games)
        rcyc.layoutManager = LinearLayoutManager(context)
        rcyc.adapter = gamesAdapter
        return view
    }
}
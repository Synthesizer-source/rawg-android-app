package com.synthesizer.source.rawg.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.synthesizer.source.rawg.R

class HomeFragment : Fragment() {

    val gamesAdapter = GamesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val rcyc = view.findViewById<RecyclerView>(R.id.games)
        rcyc.layoutManager = LinearLayoutManager(context)
        rcyc.adapter = gamesAdapter
        return view
    }
}
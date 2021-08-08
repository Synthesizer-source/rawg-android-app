package com.synthesizer.source.rawg.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.api.generateApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateApi()
    }
}
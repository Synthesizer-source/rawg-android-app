package com.synthesizer.source.rawg

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synthesizer.source.rawg.api.generateApi

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateApi()
    }
}
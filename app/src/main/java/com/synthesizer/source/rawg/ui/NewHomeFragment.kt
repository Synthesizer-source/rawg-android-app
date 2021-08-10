package com.synthesizer.source.rawg.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.databinding.FragmentNewHomeBinding

class NewHomeFragment : Fragment() {

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private var enabled = true
    private var onBackPressedCallback: OnBackPressedCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        onBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!enabled) {
                    this.remove()
                    requireActivity().onBackPressed()
                }
                backMotion()
            }
        }
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edittextfield.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
                Log.d("synthesizer-source", "backMotion: clicked! $enabled")
                view.performClick()
            }
        }

        binding.edittextfield.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                searchViewOnClick()
                return false
            }
        })
    }

    private fun searchViewOnClick() {
        enabled = true
        binding.constraintLayout.enableTransition(R.id.clickSearch, true)
        binding.constraintLayout.transitionToEnd()
        addBackButtonCallback()
    }

    private fun addBackButtonCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback!!
        )
    }

    private fun backMotion() {
        enabled = false
        binding.constraintLayout.transitionToStart()
    }
}
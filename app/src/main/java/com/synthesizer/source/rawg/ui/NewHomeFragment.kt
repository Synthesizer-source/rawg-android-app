package com.synthesizer.source.rawg.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.databinding.FragmentNewHomeBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class NewHomeFragment : Fragment() {

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private var enabled = true
    private var onBackPressedCallback: OnBackPressedCallback? = null
    private var inputManager: InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        inputManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        onBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.remove()
                if (!enabled) {
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

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, {
            if (!it) backMotion()
        })

        binding.edittextfield.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
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
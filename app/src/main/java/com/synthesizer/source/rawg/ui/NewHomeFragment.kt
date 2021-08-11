package com.synthesizer.source.rawg.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.Fragment
import com.synthesizer.source.rawg.databinding.FragmentNewHomeBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

class NewHomeFragment : Fragment() {

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private var isExpand = true
    private var onBackPressedCallback: OnBackPressedCallback? = null
    private var inputMethodManager: InputMethodManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        registerListeners()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun registerListeners() {

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, {
            if (!it && !isExpand) transitionToStart()
        })

        onBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                this.remove()
                if (isExpand) {
                    requireActivity().onBackPressed()
                } else {
                    transitionToStart()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback!!
        )

        binding.edittextfield.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
                view.performClick()
            }
        }

        binding.edittextfield.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                onSearchViewClick()
                return false
            }
        })
    }

    private fun onSearchViewClick() {
        if (binding.root.scrollY != 0) scrollToTop() else transitionToEnd()
    }

    private fun scrollToTop() {
        val duration = 500L
        val valueAnimator: ValueAnimator =
            ValueAnimator.ofInt(binding.root.scrollY, binding.root.top)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            binding.root.scrollTo(0, value)
        }

        valueAnimator.doOnStart {
            hideKeyboard()
        }

        valueAnimator.doOnEnd {
            showKeyboard()
            transitionToEnd()
            it.removeAllListeners()
        }

        valueAnimator.start()
    }

    private fun transitionToStart() {
        binding.visitWebSiteButton.isEnabled = true
        binding.constraintLayout.transitionToStart()
        isExpand = true
    }

    private fun transitionToEnd() {
        binding.visitWebSiteButton.isEnabled = false
        binding.constraintLayout.transitionToEnd()
        isExpand = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onBackPressedCallback = null
        inputMethodManager = null
    }

    private fun showKeyboard() {
        binding.edittextfield.inputType = InputType.TYPE_CLASS_TEXT
        binding.edittextfield.requestFocus()
        inputMethodManager!!.showSoftInput(binding.edittextfield, InputMethodManager.SHOW_FORCED)
    }

    private fun hideKeyboard() {
        binding.edittextfield.clearFocus()
        binding.edittextfield.setRawInputType(InputType.TYPE_NULL)
    }
}
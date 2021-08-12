package com.synthesizer.source.rawg.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.synthesizer.source.rawg.databinding.FragmentNewHomeBinding
import com.synthesizer.source.rawg.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

@AndroidEntryPoint
class NewHomeFragment : Fragment() {

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private var isExpand = true
    private var inputMethodManager: InputMethodManager? = null

    private val viewModel: NewHomeViewModel by viewModels()
    private val adapter = HomeScreenAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewHomeBinding.inflate(inflater, container, false)
        inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        hideKeyboard()
        registerListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            homeScreenGames.offscreenPageLimit = 3
            (homeScreenGames[0] as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            homeScreenGames.setPageTransformer(OffsetPageTransformer(24, 24))
            homeScreenGames.adapter = adapter
            viewModel.games.observe(viewLifecycleOwner, {
                adapter.loadDataSet(it)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.clearFocus()
        hideKeyboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        inputMethodManager = null
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun registerListeners() {

        binding.homeScreenGames.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.selectedGameImageView.loadImage(adapter.getItem(position).imageUrl)
            }
        })

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, {
            if (!it && !isExpand) transitionToStart()
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    this.remove()
                    if (isExpand) {
                        requireActivity().onBackPressed()
                    } else {
                        transitionToStart()
                    }
                }
            }
        )

        binding.searchEditText.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) {
                view.performClick()
            }
        }

        binding.searchEditText.setOnClickListener {
            if (binding.root.scrollY != 0) {
                hideKeyboard()
                scrollToTop()
            } else {
                transitionToEnd()
                showKeyboard()
            }
        }
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

        valueAnimator.doOnEnd {
            showKeyboard()
            transitionToEnd()
            it.removeAllListeners()
        }

        valueAnimator.start()
    }

    private fun transitionToStart() {
        binding.visitWebSiteButton.isEnabled = true
        binding.homeMotionLayout.transitionToStart()
        isExpand = true
    }

    private fun transitionToEnd() {
        binding.visitWebSiteButton.isEnabled = false
        binding.homeMotionLayout.transitionToEnd()
        isExpand = false
    }

    private fun showKeyboard() {
        binding.searchEditText.inputType = InputType.TYPE_CLASS_TEXT
        binding.searchEditText.requestFocus()
        inputMethodManager!!.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_FORCED)
    }

    private fun hideKeyboard() {
        inputMethodManager!!.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}
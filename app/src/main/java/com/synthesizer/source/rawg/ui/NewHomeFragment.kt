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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.databinding.FragmentNewHomeBinding
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


class NewHomeFragment : Fragment() {

    private var _binding: FragmentNewHomeBinding? = null
    private val binding get() = _binding!!
    private var isExpand = true
    private var inputMethodManager: InputMethodManager? = null
    val list = listOf<HomeScreenItem>(
        HomeScreenItem(R.drawable.rdr),
        HomeScreenItem(R.drawable.gta),
        HomeScreenItem(R.drawable.mafia2),
        HomeScreenItem(R.drawable.rdr),
        HomeScreenItem(R.drawable.gta),
    )

    private val adapter = HomeScreenAdapter(list)

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
            homeScreenGames.adapter = adapter
            (homeScreenGames[0] as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            homeScreenGames.setPageTransformer(OffsetPageTransformer(24, 24))
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
                Glide.with(requireContext())
                    .load(adapter.getItem(position).resId)
                    .override(512)
                    .into(binding.selectedGameImageView)
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
package com.synthesizer.source.rawg.ui.home

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.synthesizer.source.rawg.databinding.FragmentHomeBinding
import com.synthesizer.source.rawg.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var inputMethodManager: InputMethodManager? = null

    private val viewModel: HomeViewModel by viewModels()
    private val adapter = HomeGamesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
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
        viewModel.setState(false)
        binding.searchEditText.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        inputMethodManager = null
    }

    private fun registerListeners() {

        binding.homeScreenGames.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.selectedGameImageView.loadImage(adapter.getItem(position).imageUrl)
            }
        })

        KeyboardVisibilityEvent.setEventListener(requireActivity(), viewLifecycleOwner, {
            if (!it) viewModel.setState(false)
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    this.remove()
                    if (!viewModel.searchViewState.value!!) {
                        requireActivity().onBackPressed()
                    } else {
                        transitionToStart()
                    }
                }
            }
        )

        binding.searchEditText.setOnFocusChangeListener { view, isFocused ->
            if (view.isInTouchMode && isFocused) view.performClick()
        }

        binding.searchEditText.setOnClickListener {
            if (viewModel.searchViewState.value == false) viewModel.setState(true)
            else if (viewModel.searchViewState.value == true) viewModel.setState(false)
        }

        viewModel.searchViewState.observe(viewLifecycleOwner, {
            if (it) {
                if (binding.root.scrollY != 0) {
                    hideKeyboard()
                    scrollToTop()
                } else {
                    transitionToEnd()
                }
            } else {
                transitionToStart()
            }

            binding.visitWebSiteButton.isEnabled = !it
            binding.popularGamesButton.isEnabled = !it
            binding.bestOfTheYearGamesButton.isEnabled = !it
        })

        binding.visitWebSiteButton.setOnClickListener {
            goToRAWGWebsite()
        }

        binding.bestOfTheYearGamesButton.setOnClickListener {
            navigateToBestOfTheYear()
        }

        binding.popularGamesButton.setOnClickListener {
            navigateToPopularIn2020()
        }

        binding.seeMoreGamesButton.setOnClickListener {
            navigateToAllGames()
        }

        binding.searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    navigateToSearchResult()
                    return true
                }
                return false
            }
        })
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
        binding.homeMotionLayout.transitionToStart()
    }

    private fun transitionToEnd() {
        binding.homeMotionLayout.transitionToEnd()
    }

    private fun showKeyboard() {
        binding.searchEditText.inputType = InputType.TYPE_CLASS_TEXT
        binding.searchEditText.requestFocus()
        inputMethodManager?.showSoftInput(binding.searchEditText, InputMethodManager.SHOW_FORCED)
    }

    private fun hideKeyboard() {
        inputMethodManager?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun goToRAWGWebsite() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://rawg.io/")
        startActivity(intent)
    }

    private fun navigateToSearchResult() {
        val action =
            HomeFragmentDirections.showGames(search = binding.searchEditText.text.toString())
        findNavController().navigate(action)
    }

    private fun navigateToPopularIn2020() {
        val action = HomeFragmentDirections.showGames(
            ordering = "-metacritic,-rating",
            dates = "2020-01-01,2020-12-31"
        )
        findNavController().navigate(action)
    }


    private fun navigateToBestOfTheYear() {
        val action = HomeFragmentDirections.showGames(
            ordering = "-added",
            dates = "2021-01-01,2021-12-31"
        )

        findNavController().navigate(action)
    }

    private fun navigateToAllGames() {
        val action = HomeFragmentDirections.showGames()
        findNavController().navigate(action)
    }
}
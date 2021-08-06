package com.synthesizer.source.rawg.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synthesizer.source.rawg.databinding.FragmentEmptyBinding

class EmptyFragment : Fragment() {
    private var _binding: FragmentEmptyBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layout.onExpandAnimationFinishedCallback = {
            binding.layout.setButtonText("HIDE")
        }

        binding.layout.onCollapseAnimationFinishedCallback = {
            binding.layout.setButtonText("SHOW")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
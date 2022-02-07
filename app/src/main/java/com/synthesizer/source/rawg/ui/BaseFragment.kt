package com.synthesizer.source.rawg.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.synthesizer.source.rawg.core.domain.ErrorType
import com.synthesizer.source.rawg.ui.error.ErrorDialogListener
import com.synthesizer.source.rawg.ui.error.RetryableErrorDialog
import com.synthesizer.source.rawg.ui.error.RetryableErrorDialogListener
import com.synthesizer.source.rawg.ui.error.SingleOptionErrorDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.error.filterNotNull().distinctUntilChanged().collectLatest {
                        println(it)
                        when (it.errorType) {
                            ErrorType.RETRY -> createRetryableErrorDialog(
                                message = it.messageRes,
                                callback = it.callback
                            )
                            ErrorType.NONE -> createSingleOptionErrorDialog(it.messageRes)
                        }
                    }
                }
            }
        }
    }

    private fun createRetryableErrorDialog(@StringRes message: Int, callback: () -> Unit) {
        RetryableErrorDialog(message,
            object : RetryableErrorDialogListener {
                override fun retry() {
                    callback()
                }

                override fun cancel() {
                    navigateBack()
                }

            }).show(childFragmentManager, "dialogError")
    }

    private fun createSingleOptionErrorDialog(@StringRes message: Int) {
        SingleOptionErrorDialog(message, object : ErrorDialogListener {
            override fun cancel() {
                navigateBack()
            }

        }).show(childFragmentManager, "dialogError")
    }

    private fun navigateBack() {
        if (findNavController().previousBackStackEntry == null) {
            exitApplication()
        } else findNavController().popBackStack()
    }

    private fun exitApplication() {
        ActivityCompat.finishAffinity(requireActivity())
        exitProcess(0)
    }
}
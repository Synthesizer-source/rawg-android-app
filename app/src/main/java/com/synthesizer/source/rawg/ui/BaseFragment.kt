package com.synthesizer.source.rawg.ui

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.ui.error.ErrorDialogListener
import com.synthesizer.source.rawg.ui.error.RetryableErrorDialog
import com.synthesizer.source.rawg.ui.error.RetryableErrorDialogListener
import com.synthesizer.source.rawg.ui.error.SingleOptionErrorDialog
import com.synthesizer.source.rawg.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {

        viewModel.badRequestError.observe(viewLifecycleOwner, EventObserver {
            onBadRequestError()
        })

        viewModel.unauthorizedError.observe(viewLifecycleOwner, EventObserver {
            onUnAuthorizedError()
        })

        viewModel.unknownError.observe(viewLifecycleOwner, EventObserver {
            onUnexpectedError()
        })

        viewModel.notFoundError.observe(viewLifecycleOwner, EventObserver {
            onNotFoundError()
        })

        viewModel.timeoutException.observe(viewLifecycleOwner, EventObserver {
            onRequestTimeoutError()
        })

        viewModel.unknownHostException.observe(viewLifecycleOwner, EventObserver {
            onNoConnectionError()
        })

        viewModel.serverException.observe(viewLifecycleOwner, EventObserver {
            onServerError()
        })
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

    open fun onNoConnectionError() {
        RetryableErrorDialog(R.string.check_your_connection,
            object : RetryableErrorDialogListener {
                override fun retry() {
                    viewModel.retry()
                }

                override fun cancel() {
                    exitApplication()
                }

            }).show(childFragmentManager, "noConnectionError")
    }

    open fun onNotFoundError() {
        SingleOptionErrorDialog(R.string.not_found, object : ErrorDialogListener {
            override fun cancel() {
                navigateBack()
            }

        }).show(childFragmentManager, "notFoundError")
    }

    open fun onRequestTimeoutError() {
        RetryableErrorDialog(R.string.request_timeout,
            object : RetryableErrorDialogListener {
                override fun retry() {
                    viewModel.retry()
                }

                override fun cancel() {
                    exitApplication()
                }

            }).show(childFragmentManager, "requestTimeOutError")
    }

    open fun onServerError() {
        SingleOptionErrorDialog(R.string.the_server_is_unreachable, object : ErrorDialogListener {
            override fun cancel() {
                exitApplication()
            }

        }).show(childFragmentManager, "serverError")
    }

    open fun onUnAuthorizedError() {
        SingleOptionErrorDialog(R.string.invalid_api_key, object : ErrorDialogListener {
            override fun cancel() {
                exitApplication()
            }

        }).show(childFragmentManager, "unauthorizedError")
    }

    open fun onUnexpectedError() {
        RetryableErrorDialog(R.string.unexpected_error,
            object : RetryableErrorDialogListener {
                override fun retry() {
                    viewModel.retry()
                }

                override fun cancel() {
                    navigateBack()
                }

            }).show(childFragmentManager, "unexpectedError")
    }

    open fun onBadRequestError() {
        SingleOptionErrorDialog(R.string.bad_request, object : ErrorDialogListener {
            override fun cancel() {
                navigateBack()
            }

        }).show(childFragmentManager, "badRequestError")
    }
}
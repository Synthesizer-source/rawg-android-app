package com.synthesizer.source.rawg.ui

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    abstract val viewModel: BaseViewModel

    private var _snackbar: Snackbar? = null

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
            onUnknownError()
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

    open fun onNoConnectionError() {
        _snackbar = Snackbar.make(
            requireView(),
            getString(R.string.check_your_connection),
            LENGTH_INDEFINITE
        )
            .setAction(
                "Exit"
            ) {
                ActivityCompat.finishAffinity(requireActivity())
                exitProcess(0)
            }

        _snackbar?.show()
    }

    open fun onNotFoundError() {
        showSnackbar(getString(R.string.not_found))
    }

    open fun onRequestTimeoutError() {
        showSnackbar(getString(R.string.request_timeout))
    }

    open fun onServerError() {
        showSnackbar(getString(R.string.the_server_is_unreachable))
    }

    open fun onUnAuthorizedError() {
        showSnackbar(getString(R.string.invalid_api_key))
    }

    open fun onUnknownError() {
        showSnackbar(getString(R.string.something_went_wrong))
    }

    open fun onBadRequestError() {
        showSnackbar(getString(R.string.bad_request))
    }

    private fun showSnackbar(text: String) {
        _snackbar = Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG)
        _snackbar?.show()
    }

    override fun onDestroyView() {
        _snackbar?.dismiss()
        _snackbar = null
        super.onDestroyView()
    }
}
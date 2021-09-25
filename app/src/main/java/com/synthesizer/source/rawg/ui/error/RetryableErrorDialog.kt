package com.synthesizer.source.rawg.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import com.synthesizer.source.rawg.R
import com.synthesizer.source.rawg.databinding.DialogRetryableErrorBinding

class RetryableErrorDialog(
    @StringRes private val errorMessage: Int,
    private val retryableErrorDialogListener: RetryableErrorDialogListener
) : DialogFragment() {

    private val binding by lazy {
        DialogRetryableErrorBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.styleErrorDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        binding.errorMessage.setText(errorMessage)
        binding.retry.setOnClickListener {
            dismiss()
            retryableErrorDialogListener.retry()
        }
        binding.close.setOnClickListener {
            dismiss()
            retryableErrorDialogListener.cancel()
        }
    }
}
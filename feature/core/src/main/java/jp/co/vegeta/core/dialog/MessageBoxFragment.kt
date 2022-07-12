package jp.co.vegeta.core.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.core.R
import jp.co.vegeta.core.databinding.FragmentProcessingDialogBinding
import timber.log.Timber

/**
 * Created by vegeta on 2020/11/27.
 */
@AndroidEntryPoint
class MessageBoxFragment : DialogFragment() {

    companion object {
        private const val KEY_MESSAGE = "key_message"

        private fun newInstance(message: String): MessageBoxFragment {
            return MessageBoxFragment().apply {
                arguments = bundleOf(
                    KEY_MESSAGE to message
                )
            }
        }

        fun show(
            manager: FragmentManager,
            tag: String? = null,
            message: String
        ) {
            runCatching {
                manager
            }.onSuccess {
                manager.findFragmentByTag(tag)?.let { fragment ->
                    if ((fragment as? DialogFragment)?.dialog?.isShowing == true) {
                        Timber.d("Dialog show : isShowing")
                        return
                    }
                }
                newInstance(message).showNow(manager, tag)
            }.onFailure {
                Timber.e(it)
            }
        }

        fun hide(
            manager: FragmentManager,
            tag: String? = null
        ) {
            runCatching {
                manager
            }.onSuccess {
                it.findFragmentByTag(tag)?.let { fragment ->
                    if ((fragment as? DialogFragment)?.dialog?.isShowing == true) {
                        Timber.d("Dialog hide : isShowing")
                        fragment.dismissAllowingStateLoss()
                    }
                }
            }.onFailure {
                Timber.e(it)
            }
        }
    }

    private val message: String by lazy {
        arguments?.getString(KEY_MESSAGE) as String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return DataBindingUtil.inflate<FragmentProcessingDialogBinding>(
            inflater,
            R.layout.fragment_processing_dialog,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            this.message.text = this@MessageBoxFragment.message
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
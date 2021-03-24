package jp.co.vegeta.progressbar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.progressbar.databinding.FragmentProgressBarBinding
import jp.co.vegeta.progressbar.viewmodel.ProgressBarViewModel

/**
 * Created by vegeta on 2021/03/24.
 */
@AndroidEntryPoint
class ProgressBarFragment : Fragment(R.layout.fragment_progress_bar) {

    private val progressBarViewModel by viewModels<ProgressBarViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentProgressBarBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = progressBarViewModel
        }
    }
}
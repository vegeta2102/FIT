package jp.co.vegeta.startup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.startup.databinding.FragmentStartupBinding

@AndroidEntryPoint
class StartupFragment : Fragment(R.layout.fragment_startup) {

    private val viewModel: StartupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentStartupBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.initialize()
    }
}
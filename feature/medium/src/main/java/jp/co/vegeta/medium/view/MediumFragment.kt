package jp.co.vegeta.medium.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.medium.R
import jp.co.vegeta.medium.databinding.FragmentMediumBinding
import jp.co.vegeta.medium.view.viewmodel.MediumViewModel

/**
 * Created by vegeta on 2021/03/08.
 */

@AndroidEntryPoint
class MediumFragment : Fragment(R.layout.fragment_medium) {

    private val mediumViewModel: MediumViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMediumBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = mediumViewModel
        }
    }
}
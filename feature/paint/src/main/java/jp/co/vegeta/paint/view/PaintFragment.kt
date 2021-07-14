package jp.co.vegeta.paint.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.paint.R
import jp.co.vegeta.paint.databinding.FragmentPaintBinding
import jp.co.vegeta.paint.view.viewmodel.PaintViewModel

/**
 * Created by vegeta on 2021/07/14.
 */
@AndroidEntryPoint
class PaintFragment : Fragment(R.layout.fragment_paint) {

    private val paintViewModel: PaintViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentPaintBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = paintViewModel
        }
    }
}
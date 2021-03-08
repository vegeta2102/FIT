package jp.co.vegeta.excel_reading.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.excel_reading.R
import jp.co.vegeta.excel_reading.databinding.FragmentExcelReadingBinding
import jp.co.vegeta.excel_reading.view.viewmodel.ExcelReadingViewModel

/**
 * Created by vegeta on 2021/01/29.
 */

@AndroidEntryPoint
class ExcelReadingFragment : Fragment(R.layout.fragment_excel_reading) {

    private val viewmodel by viewModels<ExcelReadingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentExcelReadingBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = this@ExcelReadingFragment.viewmodel
        }
    }
}
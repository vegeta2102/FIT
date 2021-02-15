package jp.co.vegeta.excel_reading.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import jp.co.vegeta.excel_reading.R
import jp.co.vegeta.excel_reading.databinding.FragmentExcelReadingBinding
import kotlinx.android.synthetic.main.fragment_excel_reading.*

/**
 * Created by vegeta on 2021/01/29.
 */

class ExcelReadingFragment : Fragment(R.layout.fragment_excel_reading) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentExcelReadingBinding.bind(view)
    }
}
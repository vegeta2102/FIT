package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentWifiBinding

/**
 * Created by vegeta on 2022/02/02.
 */
@AndroidEntryPoint
class WifiFragment : Fragment(R.layout.fragment_wifi) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWifiBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.inputSearch.showSoftInputOnFocus = false
        }
    }
}
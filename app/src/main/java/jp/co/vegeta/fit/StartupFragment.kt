package jp.co.vegeta.fit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentStartupBinding

@AndroidEntryPoint
class StartupFragment : Fragment(R.layout.fragment_startup) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentStartupBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
}
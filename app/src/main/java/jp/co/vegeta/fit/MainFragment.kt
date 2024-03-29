package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMainBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        lifecycleScope.launchWhenCreated {
            excel_reading.setOnClickListener {
                navigation.navigate(R.id.main_to_excel_reading)
            }
        }
    }
}
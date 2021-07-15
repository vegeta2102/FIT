package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModels()

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
                //viewModel.showSnackBarTest()
                navigation.navigate(R.id.main_to_excel_reading)
            }
            car.setOnClickListener {
                // viewModel.showSnackBar()
                navigation.navigate(R.id.main_to_paint)
            }
            flow.setOnClickListener {
                navigation.navigate(R.id.action_global_medium)
            }
            progressbar.setOnClickListener {
                navigation.navigate(R.id.main_to_progressbar)
            }
        }
    }
}
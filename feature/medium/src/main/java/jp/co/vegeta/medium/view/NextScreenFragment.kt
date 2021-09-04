package jp.co.vegeta.medium.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.medium.R
import jp.co.vegeta.medium.databinding.FragmentNextScreenBinding
import jp.co.vegeta.medium.view.viewmodel.NextScreenViewModel

/**
 * Created by vegeta on 2021/03/12.
 */
@AndroidEntryPoint
class NextScreenFragment : Fragment(R.layout.fragment_next_screen) {
    private val nextScreenViewModel: NextScreenViewModel by viewModels()
    private val navigation: NavController by lazy {
        findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentNextScreenBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = nextScreenViewModel
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        with(nextScreenViewModel) {
            openRequest.observe(viewLifecycleOwner) {
                Log.d(TAG, "openRequest")
            }
            closeRequest.observe(viewLifecycleOwner) {
                Log.d(TAG, "closeRequest")
                navigation.previousBackStackEntry?.savedStateHandle?.set("key", true)
                navigation.popBackStack()
            }
        }
    }

    companion object {
        val TAG = NextScreenFragment::class.simpleName
    }
}
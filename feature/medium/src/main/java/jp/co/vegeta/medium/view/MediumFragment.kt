package jp.co.vegeta.medium.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.medium.R
import jp.co.vegeta.medium.databinding.FragmentMediumBinding
import jp.co.vegeta.medium.view.viewmodel.MediumViewModel
import jp.co.vegeta.medium.view.viewmodel.NextScreenViewModel

/**
 * Created by vegeta on 2021/03/08.
 */

@AndroidEntryPoint
class MediumFragment : Fragment(R.layout.fragment_medium) {

    private val navigation: NavController by lazy {
        findNavController()
    }
    private val mediumViewModel: MediumViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMediumBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = mediumViewModel
        }
        observeViewModel()
        navigateCallBack()
    }

    private fun navigateCallBack() {
        navigation.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("key")
            ?.observe(viewLifecycleOwner) {
                Log.d(TAG, it.toString())
            }
    }

    private fun observeViewModel() {
        with(mediumViewModel) {
            nextRequest.observe(viewLifecycleOwner) {
                Log.d(TAG, "Go next")
                navigation.navigate(R.id.action_to_next_screen)
            }
        }

    }

    companion object {
        val TAG = MediumFragment::class.simpleName
    }
}
package jp.co.vegeta.listview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.listview.databinding.FragmentListViewBinding

/**
 * Created by vegeta on 2021/09/04.
 */
@AndroidEntryPoint
class ListViewFragment : Fragment(R.layout.fragment_list_view) {

    private val listViewViewModel: ListViewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentListViewBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = listViewViewModel
        }
    }
}
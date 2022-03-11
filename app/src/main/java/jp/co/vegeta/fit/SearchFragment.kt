package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentSearchBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber


/**
 * Created by vegeta on 2022/02/02.
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentSearchBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = searchViewModel
            initView(it)
        }
    }

    private fun initView(binding: FragmentSearchBinding) {
        with(binding.recyclerViewWifiList) {
            layoutManager =
                object : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }
            adapter = UserListAdapter(searchViewModel)

            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                    ContextCompat.getDrawable(requireContext(), R.drawable.divider_4dp)?.let {
                        setDrawable(it)
                    }
                }
            )
            itemAnimator = null
        }
        with(binding) {
            this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        Timber.d("Vegeta newText ${it}")
                        searchViewModel.updateKeyword(it)
                    }
                    return false
                }
            })
        }
    }
}
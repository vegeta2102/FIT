package jp.co.vegeta.fit

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.fit.databinding.FragmentWifiBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by vegeta on 2022/02/02.
 */
@AndroidEntryPoint
class WifiFragment : Fragment(R.layout.fragment_wifi) {

    private val wifiViewModel: WifiViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentWifiBinding.bind(view).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = wifiViewModel
            initView(it)
        }
    }

    private fun initView(binding: FragmentWifiBinding) {
        with(binding.recyclerViewWifiList) {
            layoutManager =
                object : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }
            adapter = WifiListAdapter().also { adapter ->
                wifiViewModel.wifiList.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
                lifecycleScope.launch {
                    wifiViewModel.keyword.filterNotNull().collect {
                        Timber.d("Vegeta keyword $it")
                        adapter.filter.filter(it)
                    }
                }
            }
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
            inputSearch.doOnTextChanged { text, start, before, count ->
                Timber.d("Vegeta Text: $text, Start $start, Before $before Count $count")
                wifiViewModel.updateKeyword(text.toString())
            }
        }
    }
}
package jp.co.vegeta.fit.suggest_search

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.core.extentions.dp
import jp.co.vegeta.fit.R
import jp.co.vegeta.fit.databinding.FragmentSuggestSearchBinding

/**
 * Created by vegeta on 2022/05/18.
 */
@AndroidEntryPoint
class SuggestSearchFragment : Fragment(R.layout.fragment_suggest_search) {

    private val suggestSearchViewModel: SuggestSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycle.addObserver(suggestSearchViewModel)
        FragmentSuggestSearchBinding.bind(view).also { binding ->
            binding.viewModel = suggestSearchViewModel
            binding.lifecycleOwner = viewLifecycleOwner
            setupSuggestListAdapter(binding)
            suggestSearchViewModel.init()
        }
    }

    private fun setupSuggestListAdapter(binding: FragmentSuggestSearchBinding) {
        val suggestListAdapter = SuggestListAdapter()
        val suggestionLayoutManager: LinearLayoutManager =
            object : LinearLayoutManager(context, HORIZONTAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }

                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
        binding.recyclerViewWifiList.apply {
            layoutManager = suggestionLayoutManager
            adapter = suggestListAdapter
            addItemDecoration(
                object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        val position = parent.getChildAdapterPosition(view)
                        // Hide the divider for the first child
                        if (position == 0) {
                            outRect.setEmpty()
                        } else {
                            super.getItemOffsets(outRect, view, parent, state)
                            outRect.left = 4.dp
                        }
                    }
                }
            )
        }

        binding.previous.setOnClickListener {
            if (suggestionLayoutManager.findFirstCompletelyVisibleItemPosition() > 0) {
                suggestionLayoutManager.scrollToPosition(suggestionLayoutManager.findFirstCompletelyVisibleItemPosition() - 1)
            }
        }

        binding.next.setOnClickListener {
            if (suggestionLayoutManager.findLastCompletelyVisibleItemPosition() < suggestListAdapter.itemCount) {
                suggestionLayoutManager.scrollToPosition(suggestionLayoutManager.findLastCompletelyVisibleItemPosition() + 1)
            }
        }

        binding.recyclerViewWifiList.addOnLayoutChangeListener { view, i, i2, i3, i4, i5, i6, i7, i8 ->
            binding.previous.isEnabled =
                (suggestionLayoutManager.findFirstVisibleItemPosition() == 0
                        && suggestionLayoutManager.findFirstCompletelyVisibleItemPosition() == 0).not()
            binding.next.isEnabled =
                (suggestionLayoutManager.findLastVisibleItemPosition() == suggestListAdapter.itemCount - 1
                        && suggestionLayoutManager.findLastCompletelyVisibleItemPosition() == suggestListAdapter.itemCount - 1).not()
        }

        suggestSearchViewModel.userList.observe(viewLifecycleOwner) {
            suggestListAdapter.submitList(it)
        }
    }
}
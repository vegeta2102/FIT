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
                suggestionLayoutManager.smoothScrollToPosition(
                    binding.recyclerViewWifiList,
                    null,
                    suggestionLayoutManager.findFirstCompletelyVisibleItemPosition() - 1
                )
            }
        }

        binding.next.setOnClickListener {
            if (suggestionLayoutManager.findLastCompletelyVisibleItemPosition() < suggestListAdapter.itemCount) {
                suggestionLayoutManager.smoothScrollToPosition(
                    binding.recyclerViewWifiList,
                    null,
                    suggestionLayoutManager.findLastCompletelyVisibleItemPosition() + 1
                )
            }
        }

        binding.recyclerViewWifiList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @Override
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // Detect leading of the List
                binding.previous.isEnabled = recyclerView.canScrollHorizontally(-1)
                // Detect trailing of the List
                binding.next.isEnabled = recyclerView.canScrollHorizontally(1)
            }
        })

        suggestSearchViewModel.userList.observe(viewLifecycleOwner) {
            suggestListAdapter.submitList(it)
        }
    }
}
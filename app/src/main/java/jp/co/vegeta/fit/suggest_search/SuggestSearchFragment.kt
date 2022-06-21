package jp.co.vegeta.fit.suggest_search

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.HorizontalScrollView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.core.extentions.dp
import jp.co.vegeta.fit.R
import jp.co.vegeta.fit.databinding.FragmentSuggestSearchBinding
import timber.log.Timber

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
            initView(binding)
            observeViewModel(binding)
        }
    }

    private fun initView(binding: FragmentSuggestSearchBinding) {
        hideKeyboard()
        // binding.textAddress.setSelection(0)
        binding.textAddress.requestFocus()
        // binding.addressArea.isHorizontalScrollBarEnabled = false
    }

    private fun hideKeyboard() {
        requireActivity().currentFocus?.let { v ->
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun observeViewModel(binding: FragmentSuggestSearchBinding) {
        with(suggestSearchViewModel) {
            searchQueryText.observe(viewLifecycleOwner) {
                binding.textAddress.setText(it)
                /*binding.addressArea.post {
                    binding.addressArea.fullScroll(HorizontalScrollView.FOCUS_RIGHT)
                }*/
            }
        }

        binding.textAddress.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty().not()) {
                binding.textAddress.setSelection(text?.length ?: 0)
            }
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
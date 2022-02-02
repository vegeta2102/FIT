package jp.co.vegeta.fit

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.vegeta.core.extentions.dp
import jp.co.vegeta.fit.databinding.FragmentMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    @OptIn(ExperimentalCoroutinesApi::class)
    private val mainViewModel: MainViewModel by activityViewModels()

    private val navigation: NavController by lazy {
        findNavController()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FragmentMainBinding.bind(view).also { binding ->
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = mainViewModel
            binding.listSugesstion.apply {
                layoutManager =
                    object : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }
                adapter = AddressSuggestionAdapter().apply {
                    mainViewModel.addressSuggestionList.observe(viewLifecycleOwner) {
                        Timber.d("List Address $it")
                        submitList(it)
                    }
                }
                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.left = 12.dp
                    }
                })
                itemAnimator = null
            }
        }
    }
}

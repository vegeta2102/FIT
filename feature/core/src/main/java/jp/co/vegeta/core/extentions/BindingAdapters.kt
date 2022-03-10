package jp.co.vegeta.core.extentions

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by vegeta on 2022/03/10.
 */

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> setRecyclerViewProperties(recyclerView: RecyclerView, data: T?) {
    recyclerView.adapter?.let { adapter ->
        if (adapter is BindableAdapter<*> && data != null) {
            (adapter as BindableAdapter<T>).setData(data)
        }
    }
}
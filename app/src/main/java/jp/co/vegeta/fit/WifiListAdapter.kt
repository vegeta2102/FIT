package jp.co.vegeta.fit

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.vegeta.fit.databinding.ItemWifiBinding
import timber.log.Timber
import java.util.*


/**
 * Created by vegeta on 2022/02/02.
 */

private object DiffCallbackWifi : DiffUtil.ItemCallback<WifiItem>() {
    override fun areItemsTheSame(oldItem: WifiItem, newItem: WifiItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WifiItem, newItem: WifiItem): Boolean {
        return oldItem == newItem
    }
}

class WifiListAdapter : ListAdapter<WifiItem, WifiListAdapter.ViewHolder>(DiffCallbackWifi),
    Filterable {
    var queryText: String = ""

    class ViewHolder(
        private val binding: ItemWifiBinding,
        private val queryText: String
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(wifiItem: WifiItem) {
            val ssid = wifiItem.text
            if (queryText.isNotEmpty()) {
                Timber.d("Vegeta bind $queryText")
                val startPos: Int = ssid.toLowerCase().indexOf(queryText.toLowerCase())
                val endPos = startPos + queryText.length
                if (startPos != -1) {
                    val spannable: Spannable = SpannableString(ssid)
                    val colorStateList =
                        ColorStateList(arrayOf(intArrayOf()), intArrayOf(Color.BLUE))
                    val textAppearanceSpan =
                        TextAppearanceSpan(null, Typeface.BOLD, -1, colorStateList, null)
                    spannable.setSpan(
                        textAppearanceSpan,
                        startPos,
                        endPos,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    Timber.d("Vegeta bind set spannable")
                    binding.ssid.text = spannable
                } else {
                    binding.ssid.text = ssid
                }
            } else {
                binding.ssid.text = wifiItem.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemWifiBinding.inflate(layoutInflater, parent, false),
            queryText
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(inputText: CharSequence?): FilterResults {
                Timber.d("Vegeta performFiltering inputText ${inputText?.toString()} Current list: $currentList")
                val newList = mutableListOf<WifiItem>()
                if (inputText?.isNotEmpty() == true) {
                    queryText = inputText.toString()
                    currentList.forEach {
                        if (it.text.toLowerCase(Locale.ROOT)
                                .contains(queryText.toLowerCase(Locale.ROOT))
                        ) {
                            newList.add(it)
                        }
                    }
                }
                return FilterResults().apply {
                    count = newList.size
                    values = newList
                }
            }

            override fun publishResults(inputText: CharSequence?, filterResults: FilterResults?) {
                Timber.d("Vegeta publishResults ${filterResults?.values}")
                if (filterResults != null && filterResults.count > 0) {
                    val list = filterResults.values as? List<WifiItem>
                    submitList(list)
                    notifyDataSetChanged()
                }
            }
        }
    }
}
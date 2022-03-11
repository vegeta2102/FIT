package jp.co.vegeta.fit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import jp.co.vegeta.core.extentions.BindableAdapter
import jp.co.vegeta.fit.databinding.ItemUserBinding


/**
 * Created by vegeta on 2022/02/02.
 */

class UserListAdapter(
    private val searchViewModel: SearchViewModel
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(),
    BindableAdapter<List<UserItem>> {
    private var userItemList: List<UserItem>? = null

    class ViewHolder(
        private val binding: ItemUserBinding,
        private val searchViewModel: SearchViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserItem) {
            binding.setVariable(BR.viewModel, searchViewModel)
            binding.setVariable(BR.item, userItem)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ItemUserBinding.inflate(layoutInflater, parent, false),
            searchViewModel
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        userItemList?.let {
            holder.bind(it[position])
        }
    }

    override fun getItemCount(): Int {
        return userItemList?.count() ?: 0
    }

    override fun setData(data: List<UserItem>) {
        this.userItemList = mutableListOf<UserItem>().apply {
            addAll(data)
        }
        notifyDataSetChanged()
    }
}
package jp.co.vegeta.fit.suggest_search

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.vegeta.fit.databinding.ItemSuggestionLayoutBinding


/**
 * Created by vegeta on 2022/05/18.
 */
class SuggestListAdapter : ListAdapter<String, SuggestListAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(
        private val binding: ItemSuggestionLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.searchSuggestion.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSuggestionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        // setAnimation(holder.itemView, position)
        // makeAnimation(holder.itemView)
    }

    private var lastPosition = -1
    private fun makeAnimation(v: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 200
        v.startAnimation(anim)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in_left)
            animation.duration = 1000
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        } else if (position < lastPosition) {
            val animation: Animation =
                AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
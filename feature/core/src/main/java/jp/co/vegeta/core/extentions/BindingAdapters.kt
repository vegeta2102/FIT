package jp.co.vegeta.core.extentions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.vegeta.core.R

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

@BindingAdapter("animateBackground")
fun setBackgroundResource(view: View, isAnimate: Boolean) {
    if (isAnimate) {
        val from = ContextCompat.getColor(view.context, R.color.bg_red)
        val to = ContextCompat.getColor(view.context, R.color.bg_red_30)

        val anim = ValueAnimator()
        anim.setIntValues(from, to)
        anim.setEvaluator(ArgbEvaluator())
        anim.addUpdateListener { valueAnimator -> view.setBackgroundColor((valueAnimator.animatedValue as Int)) }
        anim.duration = 300
        anim.start()
    }
}

/*
@BindingAdapter("animatedVisibility")
fun setVisibility(view: View, visibility: Int) {
    when (visibility) {
        View.VISIBLE -> {
            view.visibility = View.VISIBLE
            view.startAnimation(
                AnimationUtils.loadAnimation(view.context, R.anim.slide_right_to_left_in)
            )
        }
        else -> {
            val animation =
                AnimationUtils.loadAnimation(view.context, R.anim.slide_left_to_right_out)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationStart(animation: Animation?) {
                }

            })
            view.startAnimation(animation)
        }
    }
}*/

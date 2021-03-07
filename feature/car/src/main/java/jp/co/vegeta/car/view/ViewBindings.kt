package jp.co.vegeta.car.view

import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BindingAdapter
import jp.co.vegeta.car.R

/**
 * Created by vegeta on 2021/02/16.
 */
@BindingAdapter("bind:animate")
fun setAnimate(view: View, visibility: Visibility?) {
    visibility ?: return
    view.visibility = when (visibility) {
        Visibility.VISIBLE -> {
            view.startAnimation(
                AnimationUtils.loadAnimation(
                    view.context,
                    R.anim.transition_up
                )
            )
            View.VISIBLE
        }
        Visibility.INVISIBLE -> View.INVISIBLE
        Visibility.GONE -> View.GONE
    }
}

enum class Visibility {
    VISIBLE,
    INVISIBLE,
    GONE,
}
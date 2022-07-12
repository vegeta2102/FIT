package jp.co.vegeta.model

import android.text.Spannable

/**
 * Created by vegeta on 2022/02/02.
 */
data class UserItem(
    val text: String,
    val spanText: Spannable? = null
)
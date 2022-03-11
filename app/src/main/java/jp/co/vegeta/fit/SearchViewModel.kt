package jp.co.vegeta.fit

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TextAppearanceSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.vegeta.core.extentions.ResourceProvider
import java.util.*
import javax.inject.Inject


/**
 * Created by vegeta on 2022/02/02.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _userList = MutableLiveData<List<UserItem>>(emptyList())
    val userList: LiveData<List<UserItem>> = _userList

    private val originUserList = mutableListOf<UserItem>().apply {
        add(UserItem("Nguyen Van A"))
        add(UserItem("Nguyen Vang B"))
        add(UserItem("Nguyen Si Luan"))
        add(UserItem("Nguyen China"))
        add(UserItem("Tran Japan"))
        add(UserItem("Nguyen The United State of America"))
        add(UserItem("England"))
        add(UserItem("France"))
        add(UserItem("Germany"))
        add(UserItem("Russia"))
        add(UserItem("Vladimir Putin"))
        add(UserItem("Barack Obama"))
        add(UserItem("Hong Min Choi"))
    }

    init {
        initAdapter()
    }

    private fun initAdapter() {
        _userList.postValue(originUserList)
    }

    fun updateKeyword(filter: String) {
        val list = if (filter.trim().isNotEmpty()) {
            mutableListOf<UserItem>().apply {
                originUserList.forEach {
                    val regex = Regex(filter.lowercase(Locale.getDefault()))
                    if (regex.containsMatchIn(it.text.lowercase(Locale.getDefault()))) {
                        add(
                            UserItem(
                                text = it.text,
                                spanText = htmlColorText(it.text, filter)
                            )
                        )
                    }
                }
            }
        } else {
            originUserList
        }
        _userList.value = list
    }

    private fun htmlColorText(text: String, filter: String): Spannable? {
        val startPos: Int =
            text.lowercase(Locale.getDefault()).indexOf(filter.lowercase(Locale.getDefault()))
        val endPos: Int = startPos + filter.length
        if (startPos != -1) {
            val spannable: Spannable = SpannableString(text)
            val colorStateList = resourceProvider.getColorStateList(R.color.teal_200)
            val textAppearanceSpan =
                TextAppearanceSpan(null, Typeface.BOLD, -1, colorStateList, null)
            spannable.setSpan(
                textAppearanceSpan,
                startPos,
                endPos,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannable
        }
        return null
    }
}
package jp.co.vegeta.car.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import jp.co.vegeta.model.SnackBar

/**
 * Created by vegeta on 2021/02/16.
 */
class SnackBarViewState(
    val snackBar: LiveData<SnackBar?>
) {

    val showSnackBar: LiveData<Visibility> = snackBar.map {
        Log.d("SnackBar", "Show or not ${it.toString()} , ${it is SnackBar.Show}")
        when (it) {
            is SnackBar.Show -> {
                Visibility.VISIBLE
            }
            else -> {
                Visibility.GONE
            }
        }
    }

    val messageContent: LiveData<String?> = snackBar.map {
        when (it) {
            is SnackBar.Show -> it.text
            else -> null
        }
    }
}
package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import jp.co.vegeta.PublishStatusRepository

/**
 * Created by vegeta on 2021/02/15.
 */
class MainViewModel @ViewModelInject constructor(
    private val publishStatusRepository: PublishStatusRepository
) : ViewModel() {

    fun onClick() {
    }
}

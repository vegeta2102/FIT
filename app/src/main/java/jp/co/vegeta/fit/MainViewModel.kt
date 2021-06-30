package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.vegeta.car.view.SnackBarViewState
import jp.co.vegeta.dialog.DialogMessageRepository
import jp.co.vegeta.model.SnackBar
import jp.co.vegeta.snackbar.SnackBarRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/02/15.
 */
class MainViewModel @ViewModelInject constructor(
    private val snackBarRepository: SnackBarRepository,
    private val dialogMessageRepository: DialogMessageRepository
) : ViewModel() {

    val requestDialogMessage = dialogMessageRepository.dialogMessageFlow.filterNotNull().asLiveData()

    val snackBarViewState: SnackBarViewState by lazy {
        SnackBarViewState(snackBar = snackBarRepository.data.asLiveData())
    }

    fun showSnackBarTest() {
        viewModelScope.launch {
            snackBarRepository.push(SnackBar.Show.PriorityPass("優先パス", 5000))
        }
    }

    fun showSnackBar() {
        viewModelScope.launch {
            createList().forEach {
                snackBarRepository.push(it)
            }
        }
    }

    private fun createList(): List<SnackBar> {
        return listOf(
            SnackBar.Show.PrefixFare("事前確定運賃の配車です", 10000),
            SnackBar.Show.DesiredTime("希望乗車時刻19:00の配車です", 2000),
            SnackBar.Show.WheelChairSlop("車いす対応車両、スロープ乗車の配車です", 1500)
        )
    }
}
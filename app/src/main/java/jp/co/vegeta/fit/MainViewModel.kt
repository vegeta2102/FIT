package jp.co.vegeta.fit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import jp.co.vegeta.dialog.DialogMessageRepository
import jp.co.vegeta.model.SnackBar
import jp.co.vegeta.snackbar.SnackBarRepository
import kotlinx.coroutines.flow.filterNotNull

/**
 * Created by vegeta on 2021/02/15.
 */
class MainViewModel @ViewModelInject constructor(
    private val snackBarRepository: SnackBarRepository,
    private val dialogMessageRepository: DialogMessageRepository
) : ViewModel() {

    val requestDialogMessage =
        dialogMessageRepository.dialogMessageFlow.filterNotNull().asLiveData()

    private fun createList(): List<SnackBar> {
        return listOf(
            SnackBar.Show.PrefixFare("事前確定運賃の配車です", 10000),
            SnackBar.Show.DesiredTime("希望乗車時刻19:00の配車です", 2000),
            SnackBar.Show.WheelChairSlop("車いす対応車両、スロープ乗車の配車です", 1500)
        )
    }
}
